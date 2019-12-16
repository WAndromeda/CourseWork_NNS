var globalIndex = -1;
var globalID = 0;
var  character = "\u0418"; //Буква "И"  в верхнем регистре для исправления ошибок кодировки в UTF-8

$(document).ready(function() {
   /* $.ajax({
        url: '/Animal_List',
        method: 'GET',
        data: "",
        success: function (json_string) {*/

    try {
        /*let html = document.getElementsByTagName('html')[0].innerHTML;
        document.getElementsByTagName('html')[0].innerHTML = changeCharacter(html);*/
        let id = "animal_table";
        let table = document.getElementById(id);
        for (let i = 1; i < table.rows.length; i++){
            let row = table.rows[i];
            if (Number(row.cells[0].innerHTML.trim()) >= globalID)
                globalID = (Number(row.cells[0].innerHTML.trim()) + 1);
            let buttonDelete = document.createElement("BUTTON");
            buttonDelete.appendChild(document.createTextNode("Удалить"));
            let buttonChange = document.createElement("BUTTON");
            buttonChange.appendChild(document.createTextNode("Изменить"));
            buttonDelete.setAttribute("class", 'mini_button');
            buttonChange.setAttribute("class", 'mini_button');
            row.append(buttonDelete);
            row.append(buttonChange);
            buttonDelete.addEventListener("click", function () {
                deleteRow(row, id);
            });
            buttonChange.addEventListener("click", function () {
                changeRow(row, id);
            });
        }

    }catch (e) {
        alert(e);
    }
        /*},
        error: function () {
            alert("ОШИБКА СЧИТЫВАНИЯ ДАННЫХ ИЗ ТАБЛИЦЫ С ЖИВОТНЫМИ");
        }*/

});

/*function changeCharacter(str) {
    str = str.replace(new RegExp(character, "g"), character);
    return str.replace(new RegExp("\�\\?" , "g"), character);
}*/

function deleteRow(row, id){
    var table = document.getElementById(id);
    $.ajax({
        url: "/Animal_List?id=" + table.rows[row.rowIndex].cells[0].innerHTML.trim(),
        method: 'DELETE',
        data: "",
        success: function () {
            try {
                table.deleteRow(row.rowIndex);
                var maxID = 0;
                for (let i = 0; i < table.rows.length; i++) {
                    if ( Number(table.rows[i].cells[0].innerHTML) > maxID){
                        maxID = table.rows[i].cells[0].innerHTML;
                    }
                }
                if (maxID < globalID)
                    globalID = (Number(maxID) + 1);
            }catch (e) {
                alert(e);
            }

        }
    });

}

function changeRow(row, id) {
    var ids = ["animal_name", "animal_longitude", "animal_weight"];
    var table = document.getElementById(id);
    var inputArray = [document.getElementById(ids[0]),
        document.getElementById(ids[1]),
        document.getElementById(ids[2])
    ];
    globalIndex = row.rowIndex;
    for (let i = 0; i < table.rows[row.rowIndex].cells.length-1; i++) {
        inputArray[i].value = table.rows[row.rowIndex].cells[i+1].innerHTML;
    }
    document.getElementById("addRow").firstChild.data = "Подтвердить изменение строки №" + globalIndex;

}

function addRowFromServer(id, animal){
    var tbody = document.getElementById(id).getElementsByTagName("TBODY")[0];
    var row = document.createElement("TR");
    var td = [];
    var data = [animal.id, animal.name, animal.longitude, animal.weight];
    try {
        if (animal.id >= globalID)
            globalID = (Number(animal.id) + 1);
    }catch (e) {
        alert(e);
    }
    for (var i = 0; i < 4; i++) {
        td.push(document.createElement("TD"));
        td[i].appendChild(document.createTextNode(data[i]));
        row.appendChild(td[i]);
    }


    var buttonDelete = document.createElement("BUTTON");
    buttonDelete.appendChild(document.createTextNode("Удалить"));

    var buttonChange = document.createElement("BUTTON");
    buttonChange.appendChild(document.createTextNode("Изменить"));

    buttonDelete.setAttribute("class", 'mini_button');
    buttonChange.setAttribute("class",  'mini_button');
    row.append(buttonDelete);
    row.append(buttonChange);
    tbody.appendChild(row);
    buttonDelete.addEventListener("click", function () {
        deleteRow(row, id);
    });
    buttonChange.addEventListener("click", function () {
        changeRow(row, id);
    });
}


function addRow(id) {
    var tbody = document.getElementById(id).getElementsByTagName("TBODY")[0];
    var row = document.createElement("TR");
    var td = [];
    var ids = ["animal_name", "animal_longitude", "animal_weight"];

    for (var i = 0; i < 3; i++){
        if (document.getElementById(ids[i]).value === null || document.getElementById(ids[i]).value === undefined || document.getElementById(ids[i]).value === ""){
            alert("Заполните все поля для добавления нового животного");
            return;
        }
    }
    if (globalIndex === -1) {
        td.push(document.createElement("TD"));
        td[0].appendChild(document.createTextNode(globalID));
        row.appendChild(td[0]);
        for (i = 1; i < 4; i++) {
            td.push(document.createElement("TD"));
            td[i].appendChild(document.createTextNode(document.getElementById(ids[i-1]).value));
            row.appendChild(td[i]);
        }

        var buttonDelete = document.createElement("BUTTON");
        buttonDelete.appendChild(document.createTextNode("Удалить"));

        var buttonChange = document.createElement("BUTTON");
        buttonChange.appendChild(document.createTextNode("Изменить"));
        buttonDelete.setAttribute("class", 'mini_button');
        buttonChange.setAttribute("class", 'mini_button');
        row.append(buttonDelete);
        row.append(buttonChange);
        tbody.appendChild(row);
        buttonDelete.addEventListener("click", function () {
            deleteRow(row, id);
        });
        buttonChange.addEventListener("click", function () {
            changeRow(row, id);
        });
    }else{
        var table = document.getElementById(id);
        var inputArray = [document.getElementById(ids[0]),
            document.getElementById(ids[1]),
            document.getElementById(ids[2])
        ];

        for (i = 1; i < table.rows[globalIndex].cells.length; i++) {
            table.rows[globalIndex].cells[i].innerHTML = inputArray[i-1].value;
        }
    }
    try {
        var index = globalIndex !== -1 ? globalIndex : row.rowIndex;
        var idRow;
        if (index === -1)
            idRow = globalID;
        else
            idRow = document.getElementById(id).rows[index].cells[0].innerHTML
    }catch (e) {
        alert(e);
    }
    $.ajax({
        url: "/Animal_List?id=" + idRow +
            "&name=" + encodeURIComponent(document.getElementById(ids[0]).value) +
            "&longitude=" + encodeURIComponent(document.getElementById(ids[1]).value) +
            "&weight=" + encodeURIComponent(document.getElementById(ids[2]).value),
        method: 'POST',
        data: "",
        success: function () {
            changeButtonDefault(ids);
            globalID++;
        },
        error: function () {
            changeButtonDefault(ids);
            globalID++;
        }
    });

}

function changeButtonDefault(ids) {
    globalIndex = -1;
    document.getElementById("addRow").firstChild.data = "Нажмите, чтобы добавить строку";
    for (let i = 0; i < 3; i++)
        document.getElementById(ids[i]).value = "";
}
