package br.ufpe.cin.middleware.services.naming;

public interface BindingIterator {

	public Binding next_one();
	public BindingList next_n(long how_many);
	public void destroy();
	
}
