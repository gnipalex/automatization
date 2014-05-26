var BACKGROUND_ID = 'lesson_window_background';
var NEW_GROUP_WINDOW_ID = 'new_group_window_id';

var curent_window_level = 0;

function showNewGroupWindow(){
    var block = document.createElement('div');
    block.setAttribute('id', NEW_GROUP_WINDOW_ID);
    var spec_select = document.getElementById('produced_speciality_select_id');
    var spec_id = spec_select.options[spec_select.selectedIndex].value;
    var sem_select = document.getElementById('produced_semester_select_id');
    var sem_id = sem_select.options[sem_select.selectedIndex].value;
    if (spec_id.length === 0 || sem_id.length === 0){
        alert('ошибка, кафедра и семестр должны быть заданы');
        return;
    }
    $.ajax({
        url: 'ajax/showNewGroupWindow',
        type: 'POST',
        async: false,
        data : {sp_id : spec_id, sem_id : sem_id}
    }).done(function(response) {
        block.innerHTML = response;
        var background = createLessonBackground();
        document.body.appendChild(background);
        document.body.appendChild(block);
        curent_window_level++;
    }).fail(function() {
        alert('ошибка при отображении окна(ajax)');
    });
}

function saveNewGroup(){
    var spec_select = document.getElementById('produced_speciality_select_id');
    var spec_id = spec_select.options[spec_select.selectedIndex].value;
    var sem_select = document.getElementById('produced_semester_select_id');
    var sem_id = sem_select.options[sem_select.selectedIndex].value;
    if (spec_id.length === 0 || sem_id.length === 0){
        alert('ошибка, кафедра и семестр должны быть заданы');
        return;
    }
    var chckb_lessons = document.getElementsByName('produced_lesson_id_chckb');
    
    var ids = '';
    for (var i=0; i<chckb_lessons.length; i++){
        if (chckb_lessons[i].checked){
            ids += chckb_lessons[i].value + ';';
        }
    }
    if (ids.length === 0){
        alert('ошибка, вы должны выбрать планы которые будут скопированы');
        return;
    }
    
    $.ajax({
        url: '',
        type: 'POST',
        async: false,
        data: {lp_ids: ids, spec_id: spec_id, sem_id: sem_id}
    }).done(function(response) {
       if (!response.success) {
           alert(response.message);
           return;
       } 
       window.location.reload();
    }).fail(function(){
        alert('(ajax) ошибка');
    });
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