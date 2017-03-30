$(document).on("click", ".checkboxes-all .all .ui-chkbox-box, .checkboxes-all .all input", function() {
    var $composite = $(this).closest(".checkboxes-all");
    var widgetAll = window.PrimeFaces.widgets[$composite.data("widget-all")];
    var widgetCheckboxes = window.PrimeFaces.widgets[$composite.data("widget-checkboxes")];

    widgetCheckboxes.inputs.prop("checked", !widgetAll.isChecked()).click();
});

$(document).on("click", ".checkboxes-all .ui-selectmanycheckbox input", function() {
    var $composite = $(this).closest(".checkboxes-all");
    var widgetAll = window.PrimeFaces.widgets[$composite.data("widget-all")];

    if (!$(this).is(":checked") && widgetAll.isChecked()) {
        widgetAll.uncheck();
    }
});