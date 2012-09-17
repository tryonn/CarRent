<%@ page import="java.io.OutputStream"
		import="br.ufpe.cin.amadeus.system.human_resources.image.Image"
		import="br.ufpe.cin.amadeus.system.facade.AmadeusSystem"
		import="br.ufpe.cin.amadeus.system.access_control.user.User" %><% 

	String id = request.getParameter("id");
	AmadeusSystem amadeus = AmadeusSystem.getInstance();
	
	User user = (User) session.getAttribute("user");
	if (id != null)
		user = amadeus.searchUser(Integer.parseInt(id));
	
	byte[] photo;
	OutputStream os;
	Image image = amadeus.searchImageByPerson(user.getPerson());
	if (image != null) {
		photo = image.getPhoto();		
		os = response.getOutputStream();
		os.write(photo);
		os.flush();
		os.close();
	} else {
		pageContext.forward("/imgs/005.jpg");
	}
%>