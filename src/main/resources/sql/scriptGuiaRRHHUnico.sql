insert into guias (id, nombre_guia, tipo_inspeccion, orden, fecha_alta, username_alta) values (SEQ_GUIAS.NEXTVAL,'RECURSOS HUMANOS', 'I.G.P.',0,trunc(sysdate),'system');

insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Cat�logo de la Jefatura Superior / Comisar�a Provincial / Distrito / Local.', 0);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Personal real que presta servicio en el momento de la Inspecci�n:
- Activo.
- 2� Actividad.
- Polic�as en pr�cticas (fechas).
- Polic�as en pr�cticas en aula abierta (fechas).
- Personal CC.GG. (en labores de atenci�n al p�blico y cu�l es su destino espec�fico).
- Personal laboral (destinos ocupa y funciones asignadas).
- Liberados sindicales: Cu�ntos, desde cu�ndo, Unidad de destino, fechas de ausencia y perjuicios ocasionan.
- Personal femenino: Distribuci�n por Unidades y puestos de responsabilidad', 1);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Pedir relaci�n nominal del personal de la plantilla donde figure el puesto de trabajo que desempe�a.', 2);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Num�ricamente, altas y bajas por categor�as los 2 �ltimos a�os (vemos la movilidad).', 3);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Por categor�as, plazas existentes y cubiertas en los 2 �ltimos CGM (plantilla atractiva o no).', 4);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Personal no disponible en el momento de la Inspecci�n:
- Baja m�dica (L/D).
- Comisi�n de servicio (desde y hasta qu� fecha, en d�nde se hallan).
- Agregados de esta plantilla a otra y VICEVERSA (fechas y Ud. de destino).
- Cursos.
- Otras causas.', 5);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Existencia de puestos NO asignados y causas de ello.', 6);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Origen del personal destinado (Academia, otras plantillas, etc.). Plantilla atractiva o no', 7);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Distribuci�n de efectivos del CNP:
- Destinados en el �rea Operativa (desglosar por Brigadas).
- Destinados en el �rea de Gesti�n (desglosar por Negociados o �reas).', 8);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Ocupaci�n del personal en 2� Actividad con respecto al Cat�logo:
- Motivaci�n de d�ficit en caso de existir.
- Distribuci�n.
- Criterios de adjudicaci�n de los puestos.', 9);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'D�ficit en Cuerpos Generales (Cat�logo respecto al personal destinado).', 10);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Tasa policial: Polic�as/1.000 habitantes. Tasa nacional______.', 11);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Adecuaci�n o no de la plantilla. Caso negativo �en qu� Unidades? Razones.', 12);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'An�lisis de la actividad', 13);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Evoluci�n del personal (�LTIMO QUINQUENIO).', 14);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Media de edad: Normal o no. Causas.', 15);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Tiempo de permanencia en la plantilla.
- Existencia o no de movilidad (antig�edad en Z). Causas. Motivaci�n, etc.
- �Destino atractivo o poco atractivo?.
- �Se cubren todas las convocatorias que salen?.', 16);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Bajas previstas por pase forzoso a segunda actividad.', 17);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'ABSENTISMO. Confeccionar cuadro.', 18);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Horas extraordinarias (peonadas): Autorizaci�n para su realizaci�n, control de las efectuadas y del personal (no sobrepasa las 80 anuales ni las 11 h. intervalo)', 19);
insert into guia_Pasos (id, id_guia, paso, orden) values (SEQ_PASOSGUIA.NEXTVAL, SEQ_GUIAS.CURRVAL,'Problem�ticas observadas. Propuestas de mejora. Problem�tica que contin�a de la anterior inspecci�n.', 20);
