var TEACHER_INFO_WINDOW_ID = 'teacher_info_window_id';
var NEW_TEACHER_WINDOW_ID = 'new_teacher_window_id';

var BACKGROUND_ID = 'lesson_window_background';

var _CTX = '';//'Automatization_netbeans_/';

var curent_window_level = 0;

function viewTeacherInfoWindow(id) {
    var window = document.createElement('div');
    window.setAttribute('id', TEACHER_INFO_WINDOW_ID);
    $.ajax({
        url: _CTX + 'ajax/showTeacherWindow',
        type: 'POST',
        async: false,
        data : {id : id}
    }).done(function(response) {
        window.innerHTML = response;
        curent_window_level++;
        var background = createLessonBackground();
        document.body.appendChild(background);
        document.body.appendChild(window);
    }).fail(function() {
        alert('ошибка при отображении окна(ajax)');
    });
}

function createLessonBackground() {
    var background = document.createElement('div');
    background.setAttribute('id', BACKGROUND_ID);
    return background;
}

function removeLessonBackground() {
    var background = document.getElementById(BACKGROUND_ID);
    if (background === null || background === undefined) {
        return;
    }
    document.body.removeChild(background);
}

function closeWindow(id) {
    var window = document.getElementById(id);
    if (window !== undefined && window !== null) {
        document.body.removeChild(window);
        if (curent_window_level === 1) {
            removeLessonBackground();
        }
        curent_window_level--;
    }
}

function deleteTeacherSolution(id){
    if (id === null || id === undefined){
        return;
    }
    $.ajax({
        url: 'ajax/deleteTeacherSolution',
        type: 'POST',
        async: false,
        data : {id : id}
    }).done(function(response){
        if (!response.success){
            alert(response.message);
            return;
        }
        var teacherIdItem = document.getElementById('info_window_teacher_id');
        loadTeacherSolutions(teacherIdItem.value);
    }).fail(function(){
        alert('(ajax) ошибка');
    });
}

function loadTeacherSolutions(id){
    if (id === null || id === undefined){
        return;
    }
    var block = document.getElementById('teacher_solutions_block');
    if (block === null || block === undefined){
        return;
    }
    $.ajax({
        url: 'ajax/loadTeacherSolutions',
        type: 'POST',
        async: false,
        data : {id : id}
    }).done(function(response){
        block.innerHTML = response;
    }).fail(function(){
        alert('(ajax) ошибка при загрузке списка решений преподавателя');
    });
}

function deleteTeacher(id){
    if (id === null || id === undefined){
        return;
    }
    if (!confirm('Удалить преподавателя и связанные с ним назначения?')){
        return;
    }
    $.ajax({
        url: 'ajax/deleteTeacher',
        type: 'POST',
        async: false,
        data: {id : id}
    }).done(function(response){
        if (!response.success){
            alert(response.message);
            return;
        }
        window.location.reload();
    }).fail(function(){
        alert('(ajax) ошибка при удалении преподавателя');
    });
}
function editTeacher(id){
    if (id === null || id === undefined){
        return;
    }
    var data = new Object();
    data.id = id;
    var firstNameItem = document.getElementById('teacher_first_name_id');
    data.firstName = firstNameItem.value;
    var lastNameItem = document.getElementById('teacher_last_name_id');
    data.lastName = lastNameItem.value;
    var middleNameItem = document.getElementById('teacher_middle_name_id');
    data.middleName = middleNameItem.value;
    var postItem = document.getElementById('teacher_post_id');
    data.post = postItem.value;
    $.ajax({
        url: 'ajax/editTeacher',
        type: 'POST',
        async: false,
        data: data
    }).done(function(response){
        if (!response.success){
            alert(response.message);
            return;
        }
        window.location.reload();
    }).fail(function(){
        alert('(ajax) ошибка при редактировании преподавателя');
    });
}

function newTeacher(){
    var block = document.createElement('div');
    block.setAttribute('id', NEW_TEACHER_WINDOW_ID);
    $.ajax({
       url: 'ajax/viewNewTeacherWindow',
       type: 'POST',
       async: false
    }).done(function(response){
        block.innerHTML = response;
        var backgr = createLessonBackground();
        document.body.appendChild(backgr);
        document.body.appendChild(block);
        curent_window_level++;
    }).fail(function(){
        alert('(ajax) ошибка');
    });
}

function saveTeacher(){
    var data = new Object();
    var firstNameItem = document.getElementById('teacher_firstname');
    data.firstName = firstNameItem.value;
    var lastNameItem = document.getElementById('teacher_lastname');
    data.lastName = lastNameItem.value;
    var middleNameItem = document.getElementById('teacher_middlename');
    data.middleName = middleNameItem.value;
    var postItem = document.getElementById('teacher_post');
    data.post = postItem.value;
    
    $.ajax({
        url: 'ajax/saveTeacher',
        type: 'POST',
        async: false,
        data : data
    }).done(function(response){
        if(!response.success){
            alert('не удалось сохранить преподавателя');
            return;
        }
        window.location.reload();  
    }).fail(function(){
        alert('(ajax) ошибка');
    });
}