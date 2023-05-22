package com.ssafy.triptube.users.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private String email;

	private String name;

	private String profilePhotoUrl;

}
