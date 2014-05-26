var LESSON_WINDOW_ID = '_lesson_window_id';
var SOLUTION_WINDOW_ID = '_solution_window_id';
var NEW_ROOM_WINDOW_ID = '_new_room_window_id';
var NEW_TEACHER_WINDOW_ID = '_new_teacher_window_id';
var SOLUTIONS_BLOCK_ID = '_solutions_block';
var LESSON_WINDOW_LESSON_ID_HIDDEN = '_lesson_id_lesson_window_hidden';

var SOLUTION_SELECT_TEACHER_ID = '_edit_solution_select_teacher';
var SOLUTION_SELECT_ROOM_ID = '_edit_solution_select_room';
var SOLUTION_SELECT_BUILDING_ID = '_edit_solution_select_building';

var SOLUTION_SOLUTION_ID_HIDDEN = '_solution_solution_id_hidden';
var SOLUTION_LESSON_ID_HIDDEN = '_solution_lesson_id_hidden';

var TEACHER_LASTNAME_ID = 'teacher_lastname';
var TEACHER_FIRSTNAME_ID = 'teacher_firstname';
var TEACHER_MIDDLENAME_ID = 'teacher_middlename';
var TEACHER_POST_ID = 'teacher_post';
var ROOM_NAME = '_roomname';

var MANY_EDIT_WINDOW_ID = 'many_edit_window_id';

var LESSON_BACKGROUND = 'lesson_window_background';

var _CTX = '';//'Automatization_netbeans_/';

//уровень окна для контроля нажатий кнопок
//если 0 - окон нет
//1 - открыто окно учебного плана
//2 - открыто окно решения
//3 - открыто окно добавления преподавателя/аудитории
var curent_window_level = 0;


function viewLesson(id) {
    if (curent_window_level > 0) {
        return;
    }
    var window = document.getElementById(LESSON_WINDOW_ID);
    if (window !== undefined && window !== null) {
        return;
    }
    window = document.createElement('div');
    window.setAttribute('id', LESSON_WINDOW_ID);
    window.setAttribute('class', 'lesson_window');
    $.ajax({
        url: _CTX + 'ajax/viewLesson',
        method: 'POST',
        async: false,
        data: {id: id}
    }).done(function(response) {
        var backgr = createLessonBackground();
        if (backgr === null) {
            alert('ошибка(background)');
            return;
        }
        window.innerHTML = response;
        curent_window_level = 1;
        document.body.appendChild(backgr);
        document.body.appendChild(window);
    }).fail(function() {
        alert('Ошибка загрузки учебного плана (ajax)');
    });

}
//подгрузит список решений с сервера
function loadSolutions(lesson_id) {
    if (lesson_id === undefined || lesson_id === null) {
        return;
    }
    var lesson_window = document.getElementById(LESSON_WINDOW_ID);
    if (lesson_window === undefined || lesson_window === null) {
        alert('Не найдено открытое окно учебного плана!');
        return;
    }
    var solutions_block = document.getElementById(SOLUTIONS_BLOCK_ID);
    if (solutions_block === undefined || solutions_block === null) {
        alert('Не найден контейнер решений в окне учебного плана!');
        return;
    }
    $.ajax({
        url: _CTX + 'ajax/getSolutions',
        type: 'POST',
        async: false,
        data: {lesson_id: lesson_id}
    }).done(function(response) {
        solutions_block.innerHTML = response;
    }).fail(function() {
        alert('ошибка при подгрузке решений(ajax)');
    });
}

//покажет окно редактирования решения
function viewSolution(id) {
    if (curent_window_level !== 1) {
        return;
    }
    var lesson_window = document.getElementById(LESSON_WINDOW_ID);
    if (lesson_window === undefined || lesson_window === null) {
        alert('не найдено окно с учебным планом');
        return;
    }
    var lesson_id_hidden = document.getElementById(LESSON_WINDOW_LESSON_ID_HIDDEN);
    if (lesson_id_hidden === undefined || lesson_id_hidden === null) {
        alert('не найден id учебного плана');
        return;
    }
    //var solution_id_hidden = document.getElementById(SOLUTION_SOLUTION_ID_HIDDEN);
    var data = new Object();
    data.lesson_id = lesson_id_hidden.value;
//    if (solution_id_hidden !== undefined && solution_id_hidden !== null){
//        data.solution_id = solution_id_hidden.value;
//    }
    if (id !== undefined && id !== null) {
        data.solution_id = id;
    }

    $.ajax({
        url: _CTX + 'ajax/viewSolution',
        type: 'POST',
        async: false,
        data: data
    }).done(function(response) {
        var window = document.createElement('div');
        window.setAttribute('id', SOLUTION_WINDOW_ID);
        window.setAttribute('class', 'solution_window');
        window.innerHTML = response;
        document.body.appendChild(window);
        curent_window_level++;
    }).fail(function() {
        alert('ошибка при получении решения (ajax');
    });

}
function saveSolution() {
    if (curent_window_level !== 2) {
        return;
    }
    //для создания/редактирования плана необходимо знать id учебного плана
    var lesson_item = document.getElementById(SOLUTION_LESSON_ID_HIDDEN);
    if (lesson_item === undefined || lesson_item === null) {
        alert('ошибка, не найден id учебного плана');
        return;
    }
    var lesson_id = document.getElementById(SOLUTION_LESSON_ID_HIDDEN).value;
    //если имеется id решения значит это редактирование
    var solution_id = '';
    var solution_item = document.getElementById(SOLUTION_SOLUTION_ID_HIDDEN);
    if (solution_item !== undefined && solution_item !== null) {
        solution_id = solution_item.value;
    }

    var teacher_select = document.getElementById(SOLUTION_SELECT_TEACHER_ID);
    var teacher_id = teacher_select.options[teacher_select.selectedIndex].value;
    var room_select = document.getElementById(SOLUTION_SELECT_ROOM_ID);
    var room_id = room_select.options[room_select.selectedIndex].value;

    var data = new Object();
    data.lesson_id = lesson_id;
    data.solution_id = solution_id;
    data.teacher_id = teacher_id;
    data.room_id = room_id;

    var chkb_lect = document.getElementById('sol_window_chckb_lect');
    if (chkb_lect !== null && chkb_lect !== undefined && chkb_lect.checked) {
        data.lect = true;
    }
    var chkb_pr = document.getElementById('sol_window_chckb_pract');
    if (chkb_pr !== null && chkb_pr !== undefined && chkb_pr.checked) {
        data.pract = true;
    }
    var chkb_lab = document.getElementById('sol_window_chckb_lab');
    if (chkb_lab !== null && chkb_lab !== undefined && chkb_lab.checked) {
        data.lab = true;
    }

    $.ajax({
        url: _CTX + 'ajax/saveSolution',
        type: 'POST',
        async: false,
        data: data
    }).done(function(response) {
        if (response.success) {
            //обновить список решений в окне учебного плана
            loadSolutions(lesson_id);
            closeWindow(SOLUTION_WINDOW_ID);
        } else {
            alert(response.message);
        }

    }).fail(function() {
        alert('ошибка при сохранении решения(ajax)');
    });

}

function deleteSolution(id) {
    if (curent_window_level !== 1) {
        return;
    }
    if (id === undefined || id === null) {
        return;
    }
    var lesson_id = document.getElementById(LESSON_WINDOW_LESSON_ID_HIDDEN);
    if (lesson_id === undefined || lesson_id === null) {
        alert('не найден id учебного плана');
        return;
    }
    $.ajax({
        url: _CTX + 'ajax/deleteSolution',
        type: 'POST',
        async: false,
        data: {solution_id: id, lesson_id: lesson_id.value}
    }).done(function(response) {
        if (response.success) {
            loadSolutions(lesson_id.value);
        } else {
            alert(response.message);
        }
    }).fail(function() {
        alert('ошибка при удалении решения(ajax)');
    });

}

function viewAddTeacher() {
    if (curent_window_level !== 2) {
        return;
    }
    var window = document.createElement('div');
    window.setAttribute('id', NEW_TEACHER_WINDOW_ID);
    window.setAttribute('class', 'new_teacher_window');
    $.ajax({
        url: _CTX + 'ajax/viewAddTeacher',
        type: 'POST'
    }).done(function(response) {
        window.innerHTML = response;
        curent_window_level++;
        document.body.appendChild(window);
    }).fail(function() {
        alert('ошибка при отображении окна(ajax)');
    });
}

function saveTeacher() {
    if (curent_window_level !== 3) {
        return;
    }
    var data = new Object();
    var last_name = document.getElementById(TEACHER_LASTNAME_ID);
    if (last_name !== undefined && last_name.value !== undefined) {
        data.lastName = last_name.value;
    }
    var first_name = document.getElementById(TEACHER_FIRSTNAME_ID);
    if (first_name !== undefined && first_name.value !== undefined) {
        data.firstName = first_name.value;
    }
    var middle_name = document.getElementById(TEACHER_MIDDLENAME_ID);
    if (middle_name !== undefined && middle_name.value !== undefined) {
        data.middleName = middle_name.value;
    }
    var post = document.getElementById(TEACHER_POST_ID);
    if (post !== undefined && post.value !== undefined) {
        data.post = post.value;
    }

    $.ajax({
        url: _CTX + 'ajax/saveTeacher',
        type: 'POST',
        async: false,
        data: data
    }).done(function(response) {
        if (!response.success) {
            alert(response.message);
            return;
        } else {
            loadTeachers();
            closeWindow(NEW_TEACHER_WINDOW_ID);
        }
    }).fail(function() {
        alert('ошибка при сохранении преподавателя(ajax)');
    });

}

function loadRooms() {
    var window = document.getElementById(SOLUTION_WINDOW_ID);
    if (window === undefined || window === null) {
        alert('не найдено окно редактирования решения');
        return;
    }
    var rooms_block = document.getElementById(SOLUTION_SELECT_ROOM_ID);
    if (rooms_block === null || rooms_block === undefined) {
        alert('не найден элемент со списком аудиторий');
        return;
    }
    var building_select = document.getElementById(SOLUTION_SELECT_BUILDING_ID);
    var build_id = building_select.options[building_select.selectedIndex].value;
    $.ajax({
        url: _CTX + 'ajax/loadRooms',
        async: false,
        type: 'POST',
        data: {b_id : build_id}
    }).done(function(response) {
        rooms_block.innerHTML = response;
    }).fail(function() {
        alert('ошибка при подгрузке аудиторий(ajax)');
    });

}

function saveRoom() {
    if (curent_window_level !== 3) {
        return;
    }
    var data = new Object();
    var name = document.getElementById(ROOM_NAME);

    if (name !== null && name !== undefined) {
        data.room = name.value;
    }

    $.ajax({
        url: _CTX + 'ajax/saveRoom',
        type: 'POST',
        async: false,
        data: data
    }).done(function(response) {
        if (response.success) {
            loadRooms();
            closeWindow(NEW_ROOM_WINDOW_ID);
        } else {
            alert(response.message);
            return;
        }
    }).fail(function() {
        alert('ошибка при сохранении(ajax)');
    });
}

function viewAddRoom() {
    if (curent_window_level !== 2) {
        return;
    }
    var window = document.createElement('div');
    window.setAttribute('id', NEW_ROOM_WINDOW_ID);
    window.setAttribute('class', 'new_room_window');
    $.ajax({
        url: _CTX + 'ajax/viewAddRoom',
        async: false,
        type: 'POST'
    }).done(function(response) {
        window.innerHTML = response;
        curent_window_level++;
        document.body.appendChild(window);
    }).fail(function() {
        alert('ошибка при отображении окна(ajax)');
    });
}

function loadTeachers() {
    var window = document.getElementById(SOLUTION_WINDOW_ID);
    if (window === undefined || window === null) {
        alert('не найдено окно редактирования решения');
        return;
    }
    var teachers_block = document.getElementById(SOLUTION_SELECT_TEACHER_ID);
    if (teachers_block === undefined || teachers_block === null) {
        alert('не найден элемент со списком преподавателей');
    }

    $.ajax({
        url: _CTX + 'ajax/loadTeachers',
        async: false,
        type: 'POST'
    }).done(function(response) {
        teachers_block.innerHTML = response;
    }).fail(function() {
        alert('ошибка при обновлении списка преподавателей(ajax)');
    });

}

//закрыть окно и уменьшить уровень открытого окна
function closeWindow(id) {
    if (id === LESSON_WINDOW_ID && curent_window_level !== 1) {
        return;
    }
    if (id === SOLUTION_WINDOW_ID && curent_window_level !== 2) {
        return;
    }
    if (id === NEW_TEACHER_WINDOW_ID && curent_window_level !== 3) {
        return;
    }
    if (id === NEW_ROOM_WINDOW_ID && curent_window_level !== 3) {
        return;
    }
    if (id === MANY_EDIT_WINDOW_ID && curent_window_level !== 1) {
        return;
    }
    var window = document.getElementById(id);
    if (window !== undefined && window !== null) {
        document.body.removeChild(window);
        if (curent_window_level === 1) {
            removeLessonBackground();
        }
        curent_window_level--;
    }
}

function createLessonBackground() {
    var wraper = document.getElementById('wraper');
    if (wraper === undefined || wraper === null) {
        return null;
    }
    //wraper.setAttribute('style', 'overflow:hidden;height:100%;');
    var background = document.createElement('div');
    background.setAttribute('id', LESSON_BACKGROUND);
    return background;
}

function removeLessonBackground() {
    var wraper = document.getElementById('wraper');
    if (wraper === undefined || wraper === null) {
        return;
    }
    //wraper.setAttribute('style', '');
    var background = document.getElementById(LESSON_BACKGROUND);
    if (background === null || background === undefined) {
        return;
    }
    document.body.removeChild(background);
}

function showChecks() {
    var item = document.getElementsByName('plan_to_edit');
    var out = "";
    for (var i = 0; i < item.length; i++) {
        if (item[i].checked) {
            out += item[i].value + " ";
        }
    }
    alert(out);
}

function markAll() {
    var item = document.getElementsByName('plan_to_edit');
    for (var i = 0; i < item.length; i++) {
        item[i].checked = true;
    }
}
function unMarkAll() {
    var item = document.getElementsByName('plan_to_edit');
    for (var i = 0; i < item.length; i++) {
        item[i].checked = false;
    }
}

function showManyEditWindow() {
    if (curent_window_level !== 0) {
        return;
    }
    var w = document.getElementById(MANY_EDIT_WINDOW_ID);
    if (w !== undefined && w !== null) {
        return;
    }
    var item = document.getElementsByName('plan_to_edit');
    var ids = "";
    var selected_count = 0;
    for (var i = 0; i < item.length; i++) {
        if (item[i].checked) {
            ids += item[i].value + ";";
            selected_count++;
        }
    }
    if (selected_count === 0) {
        return;
    }
    $.ajax({
        url: _CTX + 'ajax/getManyWindow',
        async: false,
        type: 'POST',
        data: {ids: ids}
    }).done(function(response) {
        var window = document.createElement('div');
        window.setAttribute('id', MANY_EDIT_WINDOW_ID);
        window.setAttribute('class', 'many_edit_window');
        window.innerHTML = response;
        var backgr = createLessonBackground();
        document.body.appendChild(backgr);
        document.body.appendChild(window);
        curent_window_level++;
    }).fail(function() {
        alert('ajax ошибка');
    });
}

function loadRoomsForManyEdit(){
    var build_select = document.getElementById('many_edit_building_select_id');
    var rooms_select = document.getElementById('many_edit_rooms_select_id');
    if (rooms_select === null || rooms_select === undefined){
        return;
    }
    var b_id = build_select.options[build_select.selectedIndex].value;
    if (b_id.length === 0){
        rooms_select.innerHTML = "";
        return;
    }
    $.ajax({
        url: 'ajax/loadRoomsForManyEdit',
        type: 'POST',
        async: false,
        data: {b_id : b_id}                
    }).done(function(response){
        rooms_select.innerHTML = response;
    }).fail(function(){
        alert('(ajax) ошибка');
    });
}

function saveManySolution(){
    var teacher_select = document.getElementById('teacher_select_id');
    var teacher_id = teacher_select.options[teacher_select.selectedIndex].value;
    if (teacher_id === null || teacher_id === undefined || teacher_id.length === 0){
        alert('не задан преподаватель');
        return;
    }
    var room_select = document.getElementById('many_edit_rooms_select_id');
    var room_id = '';
    if (room_select.options.length > 0){
        room_id = room_select.options[room_select.selectedIndex].value;
    }
    var chckb_lab = document.getElementsByName('many_edit_lab_chckb');
    var lab_ids = '';
    if (chckb_lab !== null && chckb_lab !== undefined){
        for(var i=0; i<chckb_lab.length; i++){
            if (chckb_lab[i].checked){
                lab_ids += chckb_lab[i].value + ';';
            }
        }
    }
    var chckb_lect = document.getElementsByName('many_edit_lection_chckb');
    var lect_ids = '';
    if (chckb_lect !== null && chckb_lect !== undefined){
        for(var i=0; i<chckb_lect.length; i++){
            if (chckb_lect[i].checked){
                lect_ids += chckb_lect[i].value + ';';
            }
        }
    }
    var chckb_pract = document.getElementsByName('many_edit_practice_chckb');
    var pract_ids = '';
    if (chckb_pract !== null && chckb_pract !== undefined){
        for(var i=0; i<chckb_pract.length; i++){
            if (chckb_pract[i].checked){
                pract_ids += chckb_pract[i].value + ';';
            }
        }
    }
    $.ajax({
        url: 'ajax/saveManyEditSolution',
        type: 'POST',
        async : false,
        data : { ids_lab: lab_ids, 
            ids_pract: pract_ids,
            ids_lect: lect_ids,
            t_id: teacher_id, r_id: room_id}
    }).done(function(response){
        if(!response.success){
            alert(response.message);
            return;
        }
        closeWindow(MANY_EDIT_WINDOW_ID);
        window.location.reload();
    }).fail(function(){
        alert('(ajax) ошибка');
    });
}