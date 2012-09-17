package br.ufpe.cin.middleware.util;
/**
 * Enum que representa todos os canais de comunicação utilizados.
 * 
 * @author Eliaquim Lima (elsn@cin.ufpe.br)
 * @author Marcelo Queiroz (mrsq@cin.ufpe.br)
 *
 */
public enum EChannels {
	RELIABLE_CONNECTION,
	RELIABLE_FILE_CONNECTION,
	NOT_RELIABLE_CONNECTION,
	WEB_CONNECTION,
	SAFETY_CONNECTION,
	PEER_TO_PEER_CONNECTION,
	MULTICAST_CONNECTION,
	BROADCAST_CONNECTION,
	SYNCHRONOUS_CONNECTION,
	ASYNCHRONOUS_CONNECTION
}
