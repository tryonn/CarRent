package br.ufpe.cin.amadeus.struts.action.academic;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;

import br.ufpe.cin.amadeus.system.academic.module.material.Material;
import br.ufpe.cin.amadeus.system.facade.AmadeusSystem;

public class MaterialDownloadAction extends DownloadAction {

	protected StreamInfo getStreamInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)	throws Exception {

		int matId = Integer.parseInt(request.getParameter("id"));
		
		AmadeusSystem amadeus = AmadeusSystem.getInstance();
		Material material = amadeus.searchMaterial(matId);
		
		String fileName = material.getName();
        // Set the content disposition
        response.setHeader("Content-disposition", 
                           "attachment; filename=\"" + fileName + "\"");
        
        return new ByteArrayStreamInfo(material);
    }
	
    protected class ByteArrayStreamInfo implements StreamInfo {
        
        protected byte[] bytes;
    	protected String contentType;
        
        public ByteArrayStreamInfo(Material material) {
            this.bytes = material.getFile();
        	this.contentType = material.getContentType();
        }
        
        public String getContentType() {
            return contentType;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(bytes);
        }
    }
}