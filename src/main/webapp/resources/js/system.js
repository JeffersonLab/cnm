var jlab = jlab || {};
jlab.editableRowTable = jlab.editableRowTable || {};
jlab.editableRowTable.entity = 'System';
jlab.editableRowTable.dialog.width = 400;
jlab.editableRowTable.dialog.height = 300;
jlab.addRow = function() {
    var code = $("#row-code").val(),
            description = $("#row-description").val(),
            reloading = false;

    $(".dialog-submit-button")
            .height($(".dialog-submit-button").height())
            .width($(".dialog-submit-button").width())
            .empty().append('<div class="button-indicator"></div>');
    $(".dialog-close-button").attr("disabled", "disabled");
    $(".ui-dialog-titlebar button").attr("disabled", "disabled");

    var request = jQuery.ajax({
        url: "/cnm/ajax/add-system",
        type: "POST",
        data: {
            code: code,
            description: description
        },
        dataType: "json"
    });

    request.done(function(json) {
        if (json.stat === 'ok') {
            reloading = true;
            window.location.reload();
        } else {
            alert(json.error);
        }
    });

    request.error(function(xhr, textStatus) {
        window.console && console.log('Unable to add system; Text Status: ' + textStatus + ', Ready State: ' + xhr.readyState + ', HTTP Status Code: ' + xhr.status);
        alert('Unable to Save: Server unavailable or unresponsive');
    });

    request.always(function() {
        if (!reloading) {
            $(".dialog-submit-button").empty().text("Save");
            $(".dialog-close-button").removeAttr("disabled");
            $(".ui-dialog-titlebar button").removeAttr("disabled");
        }
    });
};
jlab.editRow = function() {
    var code = $("#row-code").val(),
            description = $("#row-description").val(),
            id = $(".editable-row-table tr.selected-row").attr("data-id"),
            reloading = false;

    $(".dialog-submit-button")
            .height($(".dialog-submit-button").height())
            .width($(".dialog-submit-button").width())
            .empty().append('<div class="button-indicator"></div>');
    $(".dialog-close-button").attr("disabled", "disabled");
    $(".ui-dialog-titlebar button").attr("disabled", "disabled");

    var request = jQuery.ajax({
        url: "/cnm/ajax/edit-system",
        type: "POST",
        data: {
            id: id,
            code: code,
            description: description
        },
        dataType: "json"
    });

    request.done(function(json) {
        if (json.stat === 'ok') {
            reloading = true;
            window.location.reload();
        } else {
            alert(json.error);
        }
    });

    request.error(function(xhr, textStatus) {
        window.console && console.log('Unable to edit system; Text Status: ' + textStatus + ', Ready State: ' + xhr.readyState + ', HTTP Status Code: ' + xhr.status);
        alert('Unable to Save: Server unavailable or unresponsive');
    });

    request.always(function() {
        if (!reloading) {
            $(".dialog-submit-button").empty().text("Save");
            $(".dialog-close-button").removeAttr("disabled");
            $(".ui-dialog-titlebar button").removeAttr("disabled");
        }
    });
};
jlab.removeRow = function() {
    var code = $(".editable-row-table tr.selected-row td:first-child").text(),
            reloading = false;

    $("#remove-row-button")
            .height($("#remove-row-button").height())
            .width($("#remove-row-button").width())
            .empty().append('<div class="button-indicator"></div>');

    var request = jQuery.ajax({
        url: "/cnm/ajax/remove-system",
        type: "POST",
        data: {
            code: code
        },
        dataType: "json"
    });

    request.done(function(json) {
        if (json.stat === 'ok') {
            reloading = true;
            window.location.reload();
        } else {
            alert(json.error);
        }
    });

    request.error(function(xhr, textStatus) {
        window.console && console.log('Unable to remove system; Text Status: ' + textStatus + ', Ready State: ' + xhr.readyState + ', HTTP Status Code: ' + xhr.status);
        alert('Unable to Remove Server unavailable or unresponsive');
    });

    request.always(function() {
        if (!reloading) {
            $("#remove-row-button").empty().text("Remove Selected");
        }
    });
};
$(document).on("table-row-add", function() {
    jlab.addRow();
});
$(document).on("table-row-edit", function() {
    jlab.editRow();
});
$(document).on("click", "#remove-row-button", function() {
    var code = $(".editable-row-table tr.selected-row td:first-child").text();
    if (confirm('Are you sure you want to remove ' + code + '?  All dependent type codes will also be removed.')) {
        jlab.removeRow();
    }
});
$(document).on("dialogclose", "#table-row-dialog", function() {
    $("#row-form")[0].reset();
});
$(document).on("click", "#open-edit-row-dialog-button", function() {
    var $selectedRow = $(".editable-row-table tr.selected-row");
    $("#row-code").val($selectedRow.find("td:first-child").text());
    $("#row-description").val($selectedRow.find("td:nth-child(2)").text());
});
$(document).on("click", "#inventory-button", function() {
    var code = $(".editable-row-table tr.selected-row td:first-child").text();
    window.open("http://ced.acc.jlab.org/inventory/?z=&t=LineElem&ng=" + code + "%25");
});
