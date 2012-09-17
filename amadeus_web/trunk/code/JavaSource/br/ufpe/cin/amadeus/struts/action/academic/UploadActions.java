package br.ufpe.cin.amadeus.struts.action.academic;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;

import br.ufpe.cin.amadeus.dwr.academic.MaterialsDWR;
import br.ufpe.cin.amadeus.system.academic.module.homework.Homework;
import br.ufpe.cin.amadeus.system.academic.module.homework.delivery.Delivery;
import br.ufpe.cin.amadeus.system.academic.module.material.Material;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.facade.AmadeusSystem;
import br.ufpe.cin.amadeus.system.human_resources.person.Person;
import br.ufpe.cin.amadeus.util.DateConstructor;

public class UploadActions extends MappingDispatchAction {

	public ActionForward uploadMaterial(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {

		System.out.println("Entrou no upload..");
		HttpSession session = request.getSession();
		DynaValidatorForm dvForm = (DynaValidatorForm)form;
		MaterialsDWR mat = (MaterialsDWR) session.getAttribute("Materials");
		
		if (mat == null) {
			mat = new MaterialsDWR();
			session.setAttribute("Materials", mat);
		}
		
		int index = (Integer) dvForm.get("index");
		String name = (String) dvForm.get("name");
		FormFile ff = (FormFile) dvForm.get("file");

		String text = "<script type='text/javascript'>window.parent.";
		if (ff == null) {
			text += "matReplyFailed("+index+")";
		}
		else if (ff.getFileSize() == 0) {
			text += "matReplyFailed('"+ff.getFileName()+"',"+index+")";
		}
		else {
			Person author = ((User) session.getAttribute("user")).getPerson();
			Material material = new Material();
			material.setAuthor(author);
			material.setName(name);
			material.setPostDate(new Date());
			material.setFile(ff.getFileData());
			material.setContentType(ff.getContentType());
			mat.addMaterial(index, material);
			text += "matReplyAttached('"+ff.getFileName()+"',"+index+")";
			ff.destroy();
		}

		response.getWriter().write(text + "</script>");
		System.out.println("Saiu do upload..");
		return null;
	}

	public ActionForward uploadDelivery(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {

		AmadeusSystem amadeus = AmadeusSystem.getInstance();
		DynaValidatorForm dvForm = (DynaValidatorForm) form;
		User user = (User) request.getSession().getAttribute("user");
		
		Integer moduleId = (Integer) dvForm.get("moduleId");
		Integer homeworkId = (Integer) dvForm.get("homeworkId");
		FormFile ff = (FormFile) dvForm.get("file");
		
		String text = "<script type='text/javascript'>window.parent.";
		if (ff == null) {
			text += "alert('Arquivo invalido');";
		}
		else if (ff.getFileSize() == 0) {
			text += "alert('Arquivo invalido');";
		}
		else {
			Homework hw = amadeus.searchHomework(homeworkId);

			Delivery delivery = new Delivery();
			delivery.setHomework(hw);
			delivery.setPerson(user.getPerson());
			delivery.setDate(DateConstructor.today());
			delivery.setFile(ff.getFileData());
			amadeus.insertDelivery(delivery);
			text += "cancelAction(" +moduleId + ");";
			
			ff.destroy();
		}
		System.out.println(text);
		response.getWriter().write(text + "</script>");
		return null;
	}
}
