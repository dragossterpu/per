/**
 * Funciones para gestionar los checkboxes de validación de respuestas en vista /cuestionarios/validarCuestionario
 * Si pulsa seleccionar todos marcar las respuestas de ese area y viceversa
 */
$(document).ready(function(){
	$(document).on("click", ".checkArea input", function() {
		validarRespuestas($(this).closest('.checkArea'));
	});
	
	$(document).on("click", ".checkRespuesta input", function() {
		validarArea($(this).closest('.checkRespuesta'));
	});
	
	function validarRespuestas(validarArea) {
		var widgetArea = window.PrimeFaces.getWidgetById(validarArea.attr('id'));
	    var checkedArea = widgetArea.input.prop("checked");
	
	    if (checkedArea) {
	        validarArea.siblings('.prevPregCuest').find('.checkRespuesta input:enabled').each(function() {
	        	window.PrimeFaces.getWidgetById($(this).closest('.checkRespuesta').attr('id')).check();
	        });
	    } else {
	        validarArea.siblings('.prevPregCuest').find('.checkRespuesta input:enabled').each(function() {
	        	window.PrimeFaces.getWidgetById($(this).closest('.checkRespuesta').attr('id')).uncheck();
	        });
	    }
	}
					
	function validarArea(validarRespuesta) {
		var widgetRespuesta = window.PrimeFaces.getWidgetById(validarRespuesta.attr('id'));
	    var checkedRespuesta = widgetRespuesta.input.prop("checked");
	    var validarArea = validarRespuesta.closest('.prevPregCuest').siblings('.checkArea');
	    var widgetArea = window.PrimeFaces.getWidgetById(validarArea.attr('id'));
	
	    if (checkedRespuesta) {
	    	var checkedArea = true;
	        validarArea.siblings('.prevPregCuest').find('.checkRespuesta').each(function() {
	        	checkedArea = checkedArea && window.PrimeFaces.getWidgetById($(this).attr('id')).input.prop("checked");
	        });
	        if(checkedArea) { 
	        	widgetArea.check(); 
        	}
	    } else {
	        widgetArea.uncheck();
	    }
	}
});