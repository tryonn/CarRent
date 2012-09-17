package br.ufpe.cin.middleware.namingService;

import java.util.Hashtable;

import br.ufpe.cin.middleware.namingService.exceptions.AlreadyBoundException;
import br.ufpe.cin.middleware.namingService.exceptions.ENotFoundReason;
import br.ufpe.cin.middleware.namingService.exceptions.InvalidNameException;
import br.ufpe.cin.middleware.namingService.exceptions.NotFoundException;

/**
 * A name-to-object association is called a name binding.
 * A name binding is always defined relative to a naming context.
 * A naming context is an object that contains a set of name
 * bindings in which each name is unique. Different names can be
 * bound to an object in the same or different contexts at the
 * same time. There is no requirement, however, that all objects
 * must be named. To resolve a name is to determine the object
 * associated with the name in a given context. To bind a name is
 * to create a name binding in a given context. A name is always
 * resolved relative to a context - there are no absolute names.
 * Because a context is like any other object, it can also be bound
 * to a name in a naming context. Binding contexts in other contexts
 * creates a naming graph - a directed graph with nodes and labeled
 * edges where the nodes are contexts. A naming graph allows more
 * complex names to reference an object. Given a context in a naming
 * graph, a sequence of names can reference an object. This sequence
 * of names (called a compound name) defines a path in the naming graph
 * to navigate the resolution process.
 * 
 */
public class NamingContextImpl implements NamingContext {

	private Hashtable<Name,RemoteProcess> objects = new Hashtable<Name,RemoteProcess>();
	
	public void bind(Name n, RemoteProcess obj) throws NotFoundException, InvalidNameException, AlreadyBoundException {
		if (n.component.size() == 0) throw new InvalidNameException();
		if (this.objects.get(n) == null) {
			this.objects.put(n, obj);
		} else {
			throw new AlreadyBoundException();
		}
		/*
		 Creates an object binding in the naming context.
		 If a binding with the specified name already exists,
		 bind will raise an AlreadyBound exception.
		 If an implementation places limits on the number of
		 bindings within a context, bind will raise the IMP_LIMIT
		 system exception if the new binding cannot be created.
		 The operation may also raise NotFound, CannotProceed, or InvalidName.
		 */
	}

	public void rebind(Name n, RemoteProcess obj) throws NotFoundException, InvalidNameException {
		if (n.component.size() == 0) throw new InvalidNameException();
		if (this.objects.containsKey(n)) {
			RemoteProcess o = this.objects.get(n);
			if (o.getClass().equals(obj.getClass())) {
				this.objects.put(n, obj);
			} else {
				throw new NotFoundException(n.reduce_to_one());
			}
		} else {
			throw new NotFoundException(ENotFoundReason.NOT_OBJECT);
		}
		/*
		Creates an object binding in the naming context even if
		the name is already bound in the context. If already bound,
		the previous binding must be of type object; otherwise,
		a NotFound exception with a why reason of not_object is raised.
		If rebind raises a NotFound exception because an already existing
		binding is of the wrong type, the rest_of_name member of the
		exception has a sequence length of 1. The operation may also
		raise CannotProceed or InvalidName.
		 */
	}
	
	public RemoteProcess resolve(Name n) throws NotFoundException, InvalidNameException {
		if (n.component.size() == 0) throw new InvalidNameException();
		if (this.objects.containsKey(n)) {
			return this.objects.get(n);
		} else {
			throw new NotFoundException(ENotFoundReason.NOT_OBJECT);
		}
		/*
		The resolve operation retrieves an object bound to a name
		in a given context. The given name must exactly match the
		bound name. The naming service does not return the type of
		the object. Clients are responsible for "narrowing" the object
		to the appropriate type. That is, clients typically cast the
		returned object from Object to a more specialized interface.
		*/
	}

	public void unbind(Name n) throws NotFoundException, InvalidNameException {
		if (n.component.size() == 0) throw new InvalidNameException();
		if (this.objects.containsKey(n)) {
			this.objects.remove(n);
		} else {
			throw new NotFoundException(ENotFoundReason.NOT_OBJECT);
		}
		/*
		The unbind operation removes a name binding from a context.
		The operation may raise NotFound, CannotProceed, or InvalidName.
		 */
	}

}
