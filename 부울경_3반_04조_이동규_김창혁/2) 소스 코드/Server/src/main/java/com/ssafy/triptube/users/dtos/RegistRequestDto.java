package com.ssafy.triptube.users.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistRequestDto {
	private String email;
	private String password;
	private String name;
}
