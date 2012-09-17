package br.ufpe.cin.middleware.stub_skeleton.proxies;

import br.ufpe.cin.middleware.namingService.RemoteInterface;

public interface Calculator extends RemoteInterface {
	public int add  (int a, int b);
	public int sub  (int a, int b);
	public int mult (int a, int b);
	public int invert (int a);
}
