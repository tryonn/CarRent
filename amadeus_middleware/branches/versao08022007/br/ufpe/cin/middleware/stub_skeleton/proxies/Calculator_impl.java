package br.ufpe.cin.middleware.stub_skeleton.proxies;

public class Calculator_impl implements Calculator {

	private static final long serialVersionUID = 1L;

	public int add(int a, int b) {
		return a + b;
	}

	public int sub(int a, int b) {
		return a - b;
	}

	public int mult(int a, int b) {
		return a * b;
	}

	public int invert(int a) {
		return -1*a;
	}

}
