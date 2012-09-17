package br.ufpe.cin.amadeus.dwr.academic;

import static br.ufpe.cin.amadeus.dwr.academic.StrutsUtil.getModuleDWR;
import static br.ufpe.cin.amadeus.dwr.academic.StrutsUtil.getSessionBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import uk.ltd.getahead.dwr.WebContext;
import uk.ltd.getahead.dwr.WebContextFactory;
import br.ufpe.cin.amadeus.system.academic.course.Course;
import br.ufpe.cin.amadeus.system.academic.module.Module;
import br.ufpe.cin.amadeus.system.academic.module.material.Material;
import br.ufpe.cin.amadeus.system.facade.AmadeusSystem;

public class MaterialsDWR {

	private Module module;
	private AmadeusSystem amadeus;
	
	private int counter;
	private Map<Integer, Material> materials;
	private Map<Integer, Material> uploading;
	private Map<Integer, MaterialStatus> status;
	
	public MaterialsDWR() {
		System.out.println("CRIOU ACTIVITIES");
		amadeus = AmadeusSystem.getInstance();
		Course c = (Course)getSessionBean("course");
		WebContext wc = WebContextFactory.get();
		wc.getSession().setAttribute("course", c);
		status = new LinkedHashMap<Integer, MaterialStatus>();
		uploading = new LinkedHashMap<Integer, Material>();
		materials = new LinkedHashMap<Integer, Material>();
	}
	
	public Object[] init(int editing) {
		counter = 0;
		materials.clear();
		
		ModuleDWR moduleDWR = getModuleDWR();
		module = moduleDWR.getModule(editing);
		
		for (Material m : module.getMaterials()) {
			materials.put(counter++, m);
		}
		return getMaterialsData();
	}
	
	public Object[] getMaterialsData() {
		Object[] obj;
		List<Object[]> list = new ArrayList<Object[]>();
		for (Integer id : materials.keySet()) {
			obj = new Object[] {id, materials.get(id).getName(), "Material"};
			System.out.println("[" + obj[0] + ", " + obj[1] + "]");
			list.add(obj);
		}
		return list.toArray();
	}
	
	public Object[] deleteMaterial(int materialId) {
		Material m = materials.get(materialId);
		if (m != null) {
			materials.remove(materialId);
			module.getMaterials().remove(m);
			if (module.getName() != null)
				amadeus.updateModule(module);
		}
		return getMaterialsData();
	}
	
	public void initUploading() {
		status.clear();
		uploading.clear();
	}
	
	public synchronized void uploadingMaterial(int index) {
		MaterialStatus ms = status.get(index);
		if (ms == null) {
			status.put(index, MaterialStatus.EXPECTING);
		}
		System.out.println("[Material] Expecting: " + index);
	}

	public synchronized void addMaterial(int index, Material material) {
		MaterialStatus ms = status.get(index);
		if (ms != MaterialStatus.TO_DELETE) {
			uploading.put(index, material);
			status.put(index, MaterialStatus.ADDED);
		} else {
			status.put(index, MaterialStatus.DELETED);
		}
		notify();
		System.out.println("[Material] Added: " + index);
	}

	public synchronized void removeMaterial(int index) {
		MaterialStatus ms = status.get(index);
		if (ms == MaterialStatus.ADDED) {
			status.put(index, MaterialStatus.DELETED);
			uploading.remove(index);
		} else {
			status.put(index, MaterialStatus.TO_DELETE);			
		}
		System.out.println("[Material] Removed: " + index);
	}

	public synchronized Object[] persistMaterials() {
		 for (Integer index : status.keySet()) {
			MaterialStatus sts = status.get(index);
			while (sts == MaterialStatus.EXPECTING ||
					sts == MaterialStatus.TO_DELETE) {
				try {
					wait();
				} catch (InterruptedException e) {}
				sts = status.get(index);
			}
			
			if (sts == MaterialStatus.ADDED) {
				Material m = uploading.get(index);
				System.out.println("## Saving File ##");
				System.out.println("Name: " + m.getName());
				System.out.println("Content-type: " + m.getContentType());
				System.out.println("Author: " + m.getAuthor().getName());
				System.out.println("Post Date: " + m.getPostDate());
				status.put(index, MaterialStatus.PERSISTENT);
				
				module.getMaterials().add(m);
				materials.put(counter++, m);
				if (module.getName() != null)
					amadeus.updateModule(module);
			}
		}
		initUploading();
		return getMaterialsData();
	}

	private enum MaterialStatus {
		EXPECTING, ADDED, TO_DELETE, DELETED, PERSISTENT
	}
	
	//used to show the student´s view of the module	//FIXME

	public Map retrieveInfo(int currentModule) {
		Map<String, Object> m = new HashMap<String, Object>();
		Course c = (Course)getSessionBean("course");
		Module module = c.getModules().get(currentModule - 1);
		//ModuleDWR moduleDWR = getModuleDWR();
		//Module module = moduleDWR.getModule(currentModule - 1);
		Object[] mats = module.getMaterials().toArray();
		int[] matsId = new int[mats.length];
		String[] matsList = new String[mats.length];
		for (int i = 0; i < matsList.length; i++) {
			matsId[i] = ((Material) mats[i]).getId();
			matsList[i] = ((Material) mats[i]).getName();
		}
		m.put("materialsId", matsId);
		m.put("materialsList", matsList);
		m.put("moduleId", currentModule);
		return m;
	}
}