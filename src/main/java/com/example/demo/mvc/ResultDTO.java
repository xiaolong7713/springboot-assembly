package com.example.demo.mvc;

public class ResultDTO<T> {
	private String errorCode;
	private String errorMsg;
	private boolean success;
	private T data;

	public ResultDTO(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public ResultDTO() {
		
	}
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static <T> com.example.demo.mvc.ResultDTO<T> success(T data) {
		ResultDTO<T> resultDto = new ResultDTO<>();
		resultDto.setData(data);
		resultDto.setSuccess(true);
		return resultDto;
	}
}
