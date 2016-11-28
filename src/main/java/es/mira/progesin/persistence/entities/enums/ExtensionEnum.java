package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExtensionEnum {
	DOC("application/msword"), DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document"), PPT(
			"application/vnd.ms-powerpoint"), PPTX(
					"application/vnd.openxmlformats-officedocument.presentationml.presentation"), XLS(
							"application/vnd.ms-excel"), XLSX(
									"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"), JPEG(
											"image/jpeg"), PNG("image/png"), BMP("image/bmp"), PUB(
													"application/x-mspublisher"), PDF("application/pdf");

	private String contentType;

	public static ExtensionEnum getExtension(String contentType) {
		for (ExtensionEnum e : ExtensionEnum.values()) {
			if (e.contentType.equals(contentType))
				return e;
		}
		return null;
	}
}