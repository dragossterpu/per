package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ContentTypeEnum {
	DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document"), PDF("application/pdf");

	String contentType;
}
