package com.example.wallet.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.wallet.enums.WalletResponseCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoreResponse<T> {
	private String code;
	private String message;
	private T data;
	// private List<LmsError> errors;

	public static <T> ResponseEntity<CoreResponse<T>> buildWithSuccess(WalletResponseCode walletResponseCode, T data) {
		// Constructing response with success status and details
		CoreResponse<T> walletResponseEntity = new CoreResponse<>();
		walletResponseEntity.setCode(walletResponseCode.getCode());
		walletResponseEntity.setMessage(walletResponseCode.getMessage());
		walletResponseEntity.setData(data);
		return ResponseEntity.status(walletResponseCode.getHttpStatus()).body(walletResponseEntity);
	}

	public static ResponseEntity<CoreResponse<Object>> buildWithFailure(WalletResponseCode walletResponseCode) {
		// Constructing response with errors provided and data is left as null
		CoreResponse<Object> walletResponseEntity = new CoreResponse<>();
		walletResponseEntity.setCode(walletResponseCode.getCode());
		walletResponseEntity.setMessage(walletResponseCode.getMessage());
		return ResponseEntity.status(walletResponseCode.getHttpStatus()).body(walletResponseEntity);
	}

	public static <T> ResponseEntity<CoreResponse<T>> buildWithHttpStatusAndData(HttpStatus httpStatus, T data) {
		// Constructing response with http status and data
		CoreResponse<T> walletResponseEntity = new CoreResponse<>();
		walletResponseEntity.setData(data);
		return ResponseEntity.status(httpStatus).body(walletResponseEntity);
	}

	public static <T> ResponseEntity<CoreResponse<T>> buildWithHttpStatusAndDataAndMessage(HttpStatus httpStatus,
			T data, String message) {
		// Constructing response with http status and data
		CoreResponse<T> walletResponseEntity = new CoreResponse<>();
		walletResponseEntity.setData(data);
		walletResponseEntity.setMessage(message);
		return ResponseEntity.status(httpStatus).body(walletResponseEntity);
	}

}
