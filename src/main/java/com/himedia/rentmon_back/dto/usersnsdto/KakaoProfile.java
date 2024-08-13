package com.himedia.rentmon_back.dto.usersnsdto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class KakaoProfile {
	private Long id;
	private String connected_at;
	private Properties properties;
	private KakaoAccount kakao_account;

	@Data
	public static class Properties {
		private String nickname;
		private String profile_image;
		private String thumbnail_image;
	}

	@Data
	public static class KakaoAccount {
		private Boolean profile_nickname_needs_agreement;
		private Boolean profile_image_needs_agreement;
		private Profile profile;

		@Data
		public static class Profile {
			private String nickname;
			private String thumbnail_image_url;
			private String profile_image_url;
			private Boolean is_default_image;
			private Boolean is_default_nickname;  // 추가된 필드
		}
	}
}
