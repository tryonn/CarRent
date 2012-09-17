package br.ufpe.cin.amadeus.dwr.access_control;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import uk.ltd.getahead.dwr.WebContextFactory;
import br.ufpe.cin.amadeus.dwr.academic.StrutsUtil;
import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.facade.AmadeusSystem;
import br.ufpe.cin.amadeus.system.human_resources.user_request.UserRequest;
import br.ufpe.cin.amadeus.util.MailSender;


public class RequestValidation {

	public void approveRequest(int requestId) {
		HttpSession session = WebContextFactory.get().getSession();
		User user = (User) session.getAttribute("user");
		AmadeusSystem amadeus = AmadeusSystem.getInstance();
		
		final UserRequest request = amadeus.searchUserRequest(requestId); 
		amadeus.approveRequest(request, user.getPerson());
		
		Runnable r = new Runnable() {
			public void run() {
				String subject = "Amadeus: " + getRequestType(request);
				String message = "Your request has been approved.";
				try {
					MailSender.sendMail(request.getPerson(), subject, message);
				} catch (MessagingException e) {e.printStackTrace();}
			}
		};
		new Thread(r).start();
	}
	
	public String disapproveRequest(int requestId, final String justification) {
		if (justification == null || justification.trim().length() == 0) {
			return StrutsUtil.getMessage("errors.missingJustification");
		}
		HttpSession session = WebContextFactory.get().getSession();
		User user = (User) session.getAttribute("user");
		AmadeusSystem amadeus = AmadeusSystem.getInstance();
		
		final UserRequest request = amadeus.searchUserRequest(requestId); 
		amadeus.disapproveRequest(request, user.getPerson());

		Runnable r = new Runnable() {
			public void run() {
				String subject = "Amadeus: " + getRequestType(request);
				String message = "Your request has been disapproved.\n\n";
				message += "Justification:\n" + justification;
				try {
					MailSender.sendMail(request.getPerson(), subject, message);
				} catch (MessagingException e) {e.printStackTrace();}
			}
		};
		new Thread(r).start();
		return null;
	}
	
	private String getRequestType(UserRequest request) {
		if (request.isTeachingRequest()) {
			return "Teaching Request";
		} else {
			return "Assistance Request";
		}
	}
}
