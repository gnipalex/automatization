var TEACHER_WINDOW_ID = 'teacher_window_id';
var BACKGROUND_ID = 'background_window_id';
var SOLUTIONS_BLOCK_ID = 'solutions_block_id';

function createBackground(){
    var background = document.getElementById(BACKGROUND_ID);
    if (background !== null && background !== undefined){
        return;
    }
    background = document.createElement('div');
    background.setAttribute('id', BACKGROUND_ID);
    return background;
}

function removeBackground(){
    var background = document.getElementById(BACKGROUND_ID);
    if (background !== null && background !== undefined){
        return;
    }
    document.removeChild(background);
}

function loadTeachers(){

}

function viewTeacher(id){
    if (id === undefined || id === null){
        alert('не задан id преподавателя');
        return;
    }
    var window = document.getElementById(TEACHER_WINDOW_ID);
    if (window !== undefined || window !== null){
        return;
    }
    $.ajax({
        url: 'manage/..',
        type: 'POST',
        data: {id : id}
    }).done(function(response){
        
    }).fail(function(){
        
    });
}

function editTeacher(){
    
}

function closeWindow(id){
    
}

