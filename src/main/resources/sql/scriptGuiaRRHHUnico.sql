insert into guias (id, nombre_guia, tipo_inspeccion, orden, fecha_alta, username_alta) values (SEQ_GUIAS.NEXTVAL,'RECURSOS HUMANOS', 'I.G.P.',0,trunc(sysdate),'system');

insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Catálogo de la Jefatura Superior / Comisaría Provincial / Distrito / Local.', 0);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Personal real que presta servicio en el momento de la Inspección:
- Activo.
- 2ª Actividad.
- Policías en prácticas (fechas).
- Policías en prácticas en aula abierta (fechas).
- Personal CC.GG. (en labores de atención al público y cuál es su destino específico).
- Personal laboral (destinos ocupa y funciones asignadas).
- Liberados sindicales: Cuántos, desde cuándo, Unidad de destino, fechas de ausencia y perjuicios ocasionan.
- Personal femenino: Distribución por Unidades y puestos de responsabilidad', 1);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Pedir relación nominal del personal de la plantilla donde figure el puesto de trabajo que desempeña.', 2);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Numéricamente, altas y bajas por categorías los 2 últimos años (vemos la movilidad).', 3);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Por categorías, plazas existentes y cubiertas en los 2 últimos CGM (plantilla atractiva o no).', 4);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Personal no disponible en el momento de la Inspección:
- Baja médica (L/D).
- Comisión de servicio (desde y hasta qué fecha, en dónde se hallan).
- Agregados de esta plantilla a otra y VICEVERSA (fechas y Ud. de destino).
- Cursos.
- Otras causas.', 5);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Existencia de puestos NO asignados y causas de ello.', 6);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Origen del personal destinado (Academia, otras plantillas, etc.). Plantilla atractiva o no', 7);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Distribución de efectivos del CNP:
- Destinados en el Área Operativa (desglosar por Brigadas).
- Destinados en el Área de Gestión (desglosar por Negociados o Áreas).', 8);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Ocupación del personal en 2ª Actividad con respecto al Catálogo:
- Motivación de déficit en caso de existir.
- Distribución.
- Criterios de adjudicación de los puestos.', 9);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Déficit en Cuerpos Generales (Catálogo respecto al personal destinado).', 10);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Tasa policial: Policías/1.000 habitantes. Tasa nacional______.', 11);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Adecuación o no de la plantilla. Caso negativo ¿en qué Unidades? Razones.', 12);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Análisis de la actividad', 13);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Evolución del personal (ÚLTIMO QUINQUENIO).', 14);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Media de edad: Normal o no. Causas.', 15);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Tiempo de permanencia en la plantilla.
- Existencia o no de movilidad (antigüedad en Z). Causas. Motivación, etc.
- ¿Destino atractivo o poco atractivo?.
- ¿Se cubren todas las convocatorias que salen?.', 16);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Bajas previstas por pase forzoso a segunda actividad.', 17);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ABSENTISMO. Confeccionar cuadro.', 18);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Horas extraordinarias (peonadas): Autorización para su realización, control de las efectuadas y del personal (no sobrepasa las 80 anuales ni las 11 h. intervalo)', 19);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Problemáticas observadas. Propuestas de mejora. Problemática que continúa de la anterior inspección.', 20);
