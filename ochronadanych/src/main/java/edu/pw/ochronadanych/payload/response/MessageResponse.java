package edu.pw.ochronadanych.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageResponse {
	private String message;
	private String code;

	public MessageResponse(String message) {
		this.message = message;
	}
}
