var jlab = jlab || {};
jlab.editableRowTable = jlab.editableRowTable || {};
jlab.editableRowTable.entity = 'Type';
jlab.editableRowTable.dialog.width = 400;
jlab.editableRowTable.dialog.height = 300;
jlab.editableAttributeRow = '<tr><td><span class="sort-handle ui-icon ui-icon-carat-2-n-s"></span></td><td><input type="text" value=""/></td><td><input type="text" value=""/></td><td><select><option>Text</option><option>Number</option><option>Yes/No</option><option>Link</option><option disabled="disabled">Date/Time</option></select></td><td><input type="text" value=""/></td><td><span class="small-icon delete-icon" title="Delete Attribute"></span></td></tr>';
jlab.addRow = function () {
    var scode = $("#row-scode").val(),
            vvcode = $("#row-vvcode").val(),
            description = $("#row-description").val(),
            grouping = $("#row-grouping").val(),
            reloading = false;

    $(".dialog-submit-button")
            .height($(".dialog-submit-button").height())
            .width($(".dialog-submit-button").width())
            .empty().append('<div class="button-indicator"></div>');
    $(".dialog-close-button").attr("disabled", "disabled");
    $(".ui-dialog-titlebar button").attr("disabled", "disabled");

    var request = jQuery.ajax({
        url: "/cnm/ajax/add-type",
        type: "POST",
        data: {
            scode: scode,
            vvcode: vvcode,
            description: description,
            grouping: grouping
        },
        dataType: "json"
    });

    request.done(function (json) {
        if (json.stat === 'ok') {
            reloading = true;
            window.location.reload();
        } else {
            alert(json.error);
        }
    });

    request.fail(function (xhr, textStatus) {
        window.console && console.log('Unable to add type; Text Status: ' + textStatus + ', Ready State: ' + xhr.readyState + ', HTTP Status Code: ' + xhr.status);
        alert('Unable to Save: Server unavailable or unresponsive');
    });

    request.always(function () {
        if (!reloading) {
            $(".dialog-submit-button").empty().text("Save");
            $(".dialog-close-button").removeAttr("disabled");
            $(".ui-dialog-titlebar button").removeAttr("disabled");
        }
    });
};
jlab.editRow = function () {
    var scode = $("#row-scode").val(),
            vvcode = $("#row-vvcode").val(),
            description = $("#row-description").val(),
            grouping = $("#row-grouping").val(),
            id = $(".editable-row-table tr.selected-row").attr("data-id"),
            reloading = false;

    $(".dialog-submit-button")
            .height($(".dialog-submit-button").height())
            .width($(".dialog-submit-button").width())
            .empty().append('<div class="button-indicator"></div>');
    $(".dialog-close-button").attr("disabled", "disabled");
    $(".ui-dialog-titlebar button").attr("disabled", "disabled");

    var request = jQuery.ajax({
        url: "/cnm/ajax/edit-type",
        type: "POST",
        data: {
            id: id,
            scode: scode,
            vvcode: vvcode,
            description: description,
            grouping: grouping
        },
        dataType: "json"
    });

    request.done(function (json) {
        if (json.stat === 'ok') {
            reloading = true;
            window.location.reload();
        } else {
            alert(json.error);
        }
    });

    request.fail(function (xhr, textStatus) {
        window.console && console.log('Unable to edit type; Text Status: ' + textStatus + ', Ready State: ' + xhr.readyState + ', HTTP Status Code: ' + xhr.status);
        alert('Unable to Save: Server unavailable or unresponsive');
    });

    request.always(function () {
        if (!reloading) {
            $(".dialog-submit-button").empty().text("Save");
            $(".dialog-close-button").removeAttr("disabled");
            $(".ui-dialog-titlebar button").removeAttr("disabled");
        }
    });
};
jlab.removeRow = function () {
    var scode = $(".editable-row-table tr.selected-row td:first-child").text(),
            vvcode = $(".editable-row-table tr.selected-row td:nth-child(2)").text(),
            reloading = false;

    $("#remove-row-button")
            .height($("#remove-row-button").height())
            .width($("#remove-row-button").width())
            .empty().append('<div class="button-indicator"></div>');

    var request = jQuery.ajax({
        url: "/cnm/ajax/remove-type",
        type: "POST",
        data: {
            scode: scode,
            vvcode: vvcode
        },
        dataType: "json"
    });

    request.done(function (json) {
        if (json.stat === 'ok') {
            reloading = true;
            window.location.reload();
        } else {
            alert(json.error);
        }
    });

    request.fail(function (xhr, textStatus) {
        window.console && console.log('Unable to remove type; Text Status: ' + textStatus + ', Ready State: ' + xhr.readyState + ', HTTP Status Code: ' + xhr.status);
        alert('Unable to Remove Server unavailable or unresponsive');
    });

    request.always(function () {
        if (!reloading) {
            $("#remove-row-button").empty().text("Remove Selected");
        }
    });
};
jlab.filterGroupingBySystem = function (sCode) {
    if (jlab.isRequest()) {
        window.console && console.log("Ajax already in progress");
        return;
    }

    jlab.requestStart();

    var request = jQuery.ajax({
        url: jlab.contextPath + "/ajax/filter-type-grouping-by-system",
        type: "GET",
        data: {
            code: sCode
        },
        dataType: "json"
    });

    request.done(function (json) {
        if (json.stat !== "ok") {
            alert('Unable to filter grouping : ' + json.error);
        } else {
            /* Success */
            var $select = $("#grouping");
            $select.hide();
            $select.empty();
            $select.append('<option></option>');
            $(json.options).each(function () {
                $select.append('<option value="' + String(this.value).encodeXml() + '">' + String(this.name).encodeXml() + '</option>');
            });

            if ($(json.optionList).length === 1) {
                $select.prop('selectedIndex', 1);
            }

            $select.slideDown();
        }

    });

    request.fail(function (xhr, textStatus) {
        window.console && console.log('Unable to filter system list: Text Status: ' + textStatus + ', Ready State: ' + xhr.readyState + ', HTTP Status Code: ' + xhr.status);
        alert('Unable to filter system list; server did not handle request');
    });

    request.always(function () {
        jlab.requestEnd();
    });
};
jlab.showAtrributeTable = function () {
    var $html = $('<table class="attribute-table"><tbody></tbody></table>');

    $.each(jlab.attributes, function () {
        var units = this.units || "",
                value = this.value || "",
                name = this.name || "",
                type = this.type || "";

        if (type === 'Yes/No') {
            if (value === true) {
                value = "Yes";
            } else {
                value = "No";
            }
        } else if(type === 'Link') {
            value = '<a target="_blank" href="' + value + '" title="' + value + '">Link</a>';
        }

        $html.find("tbody").append('<tr><td>' + name + '</td><td>' + value + ' ' + units + '</td></tr>');
    });

    $("#attributes-panel").html($html);
};
jlab.showEditableAttributeTable = function () {
    var $html = $('<table class="editable-attribute-table"><thead><tr><th></th><th>Name</th><th>Value</th><th>Data Type</th><th>Units</th><th></th></tr></thead><tbody></tbody></table>');

    if (jlab.attributes === '') {
        jlab.attributes = [{name: '', value: ''}];
    }

    $.each(jlab.attributes, function () {
        var units = this.units || "",
                value = this.value || "",
                type = this.type || "",
                name = this.name || "",
                $row = $(jlab.editableAttributeRow);

        if (type === '') {
            type = 'Text';
        } else if (type === 'Yes/No') {
            if (value === true) {
                value = "Yes";
            } else {
                value = "No";
            }
        }

        $row.find("td:nth-child(2) input").val(name);
        $row.find("td:nth-child(3) input").val(value);
        $row.find("td:nth-child(4) select").val(type);
        $row.find("td:nth-child(5) input").val(units);

        $html.find("tbody").append($row);
    });

    $("#editable-attributes-panel").html($html);
};
jlab.resetAttributeDialog = function () {
    $("#attributes-dialog").removeClass("editable-attributes");
    $("#editable-attributes-panel").empty();
};
jlab.saveAttributes = function () {

    var reloading = false,
            attributes = [],
            validationError = false;

    $(".editable-attribute-table tbody tr").each(function () {
        var name = $(this).find("td:nth-child(2) input").val(),
                value = $(this).find("td:nth-child(3) input").val(),
                type = $(this).find("td:nth-child(4) select").val(),
                units = $(this).find("td:nth-child(5) input").val();

        name = name.trim();

        if (name !== '') {

            if (type === 'Number') {
                if (!$.isNumeric(value)) {
                    alert(name + ' value "' + value + '" is not a number');
                    validationError = true;
                    return;
                } else {
                    value = value * 1; // cast to number type
                }
            } else if (type === 'Yes/No') {
                if (!(value === 'Yes' || value === 'No')) {
                    alert(name + ' value "' + value + '" does not match "Yes" or "No"');
                    validationError = true;
                    return;
                } else {
                    value = (value === "Yes" ? true : false); // cast to boolean type
                }
            } else if (type === 'Link') {
                var uri = new URI(value);
                if(!(uri.protocol() === 'http' || uri.protocol() === 'https')) {
                    alert(name + ' value "' + value + '" does not start with http(s)://');
                    validationError = true;
                    return;
                }
                if(uri.hostname() === null || uri.hostname() === '') {
                    alert(name + ' value "' + value + '" does not contain a hostname');
                    validationError = true;
                    return;
                }
            }

            attributes.push({name: name, value: value, type: type, units: units});
        }
    });

    if (validationError) {
        return;
    }

    $("#editable-attributes-dialog .dialog-submit-button")
            .height($("#editable-attributes-dialog .dialog-submit-button").height())
            .width($("#editable-attributes-dialog .dialog-submit-button").width())
            .empty().append('<div class="button-indicator"></div>');
    $("#editable-attributes-dialog .dialog-close-button").attr("disabled", "disabled");
    $("#editable-attributes-dialog .ui-dialog-titlebar button").attr("disabled", "disabled");

    var request = jQuery.ajax({
        url: "/cnm/ajax/edit-attributes",
        type: "POST",
        data: {
            id: jlab.typeIdBeingEdited,
            attributes: JSON.stringify(attributes)
        },
        dataType: "json"
    });

    request.done(function (json) {
        if (json.stat === 'ok') {
            reloading = true;
            window.location.reload();
        } else {
            alert(json.error);
        }
    });

    request.fail(function (xhr, textStatus) {
        window.console && console.log('Unable to save attributes; Text Status: ' + textStatus + ', Ready State: ' + xhr.readyState + ', HTTP Status Code: ' + xhr.status);
        alert('Unable to Save: Server unavailable or unresponsive');
    });

    request.always(function () {
        if (!reloading) {
            $("#editable-attributes-dialog .dialog-submit-button").empty().text("Save");
            $("#editable-attributes-dialog .dialog-close-button").removeAttr("disabled");
            $("#editable-attributes-dialog .ui-dialog-titlebar button").removeAttr("disabled");
        }
    });
};
$(document).on("click", ".editable-attribute-table .delete-icon", function () {
    var $tr = $(this).closest("tr"),
            name = $tr.find("td:nth-child(2) input").val();
    name = name.trim();
    if (name !== '') {
        name = ' "' + name + '"';
    }
    if (confirm('Are you sure you want to delete' + name + '?')) {
        $tr.remove();
    }
});
$(document).on("click", "#add-attribute-button", function () {
    $(".editable-attribute-table tbody").append(jlab.editableAttributeRow);

    var div = $("#edit-attributes-wrapper")[0];
    var scrollHeight = Math.max(div.scrollHeight, div.clientHeight);
    div.scrollTop = scrollHeight - div.clientHeight;
});
$(document).on("click", ".default-clear-panel", function () {
    $("#system").val('').trigger('change');
    $("#type").val('');
    $("#grouping").val('');
    return false;
});
$(document).on("table-row-add", function () {
    jlab.addRow();
});
$(document).on("table-row-edit", function () {
    jlab.editRow();
});
$(document).on("click", "#remove-row-button", function () {
    var scode = $(".editable-row-table tr.selected-row td:first-child").text(),
            vvcode = $(".editable-row-table tr.selected-row td:nth-child(2)").text();
    if (confirm('Are you sure you want to remove ' + scode + vvcode + '?')) {
        jlab.removeRow();
    }
});
$(document).on("dialogclose", "#table-row-dialog", function () {
    $("#row-form")[0].reset();
});
$(document).on("click", "#open-edit-row-dialog-button", function () {
    var $selectedRow = $(".editable-row-table tr.selected-row");
    $("#row-scode").val($selectedRow.find("td:first-child").text());
    $("#row-vvcode").val($selectedRow.find("td:nth-child(2)").text());
    $("#row-description").val($selectedRow.find("td:nth-child(3)").text());
    $("#row-grouping").val($selectedRow.find("td:nth-child(4)").text());
});
$(document).on("click", "#inventory-button", function () {
    var scode = $(".editable-row-table tr.selected-row td:first-child").text(),
            vvcode = $(".editable-row-table tr.selected-row td:nth-child(2)").text();
    window.open(jlab.cedServerUrl + "/inventory?z=&t=LineElem&ng=" + scode + vvcode + "%25");
});
$(document).on("click", "#editable-attributes-button", function () {
    var $dialog = $("#editable-attributes-dialog"),
            $tr = $(".editable-row-table tr.selected-row"),
            system = $tr.find("td:first-child").text(),
            type = $tr.find("td:nth-child(2)").text(),
            id = $tr.attr("data-id"),
            attributes = $tr.attr("data-attributes");

    jlab.typeIdBeingEdited = id;

    $dialog.dialog({title: system + type + " Attributes"});

    jlab.resetAttributeDialog();

    if (attributes === '') {
        jlab.attributes = '';
    } else {
        /*$("#attributes-panel").text(attributes);*/
        var json = $.parseJSON(attributes);

        jlab.attributes = json;
    }

    jlab.showEditableAttributeTable();

    $dialog.dialog('open');

    $(".editable-attribute-table tbody").sortable({
        handle: ".sort-handle",
        items: "> tr"
    });
    //$( ".editable-attribute-table tbody" ).disableSelection();    
});
$(document).on("click", "#attributes-button", function () {
    var $dialog = $("#attributes-dialog"),
            $tr = $(".editable-row-table tr.selected-row"),
            system = $tr.find("td:first-child").text(),
            type = $tr.find("td:nth-child(2)").text(),
            attributes = $tr.attr("data-attributes");

    $dialog.dialog({title: system + type + " Attributes"});

    if (attributes === '' || attributes === '[]') {
        $("#attributes-panel").text('No Attributes Defined');
        jlab.attributes = '';
    } else {
        /*$("#attributes-panel").text(attributes);*/
        var json = $.parseJSON(attributes);

        jlab.attributes = json;

        jlab.showAtrributeTable();
    }

    $dialog.dialog('open');
});
$(document).on("change", "#system", function () {
    var sCode = $(this).val();
    jlab.filterGroupingBySystem(sCode);
});
$(document).on("click", "#edit-save-button", function () {
    jlab.saveAttributes();
});
$(function () {
    $("#attributes-dialog").dialog({
        width: 450,
        minWidth: 450,
        height: 300,
        minHeight: 300,
        autoOpen: false,
        resizable: false
    });
    $("#editable-attributes-dialog").dialog({
        width: 850,
        minWidth: 850,
        height: 600,
        minHeight: 600,
        autoOpen: false,
        modal: true,
        resizable: false
    });
});