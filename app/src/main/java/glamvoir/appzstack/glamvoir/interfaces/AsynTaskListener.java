package glamvoir.appzstack.glamvoir.interfaces;

import java.util.List;

public interface AsynTaskListener {
	void success(String success, String listenerId);
	void error(String errorMessage, String errorCode, String listenerId);
	void successWithresult(List<Object> sucessObject, String message, String listenerId);
}
