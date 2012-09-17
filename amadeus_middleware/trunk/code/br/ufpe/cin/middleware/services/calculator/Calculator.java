package br.ufpe.cin.middleware.services.calculator;

import br.ufpe.cin.middleware.services.naming.RemoteInterface;

public interface Calculator extends RemoteInterface {
	public int add  (int a, int b);
	public int sub  (int a, int b);
	public int mult (int a, int b);
	public int invert (int a);
}
