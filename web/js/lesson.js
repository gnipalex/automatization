var LESSON_WINDOW_ID = '_lesson_window_id';
var SOLUTION_WINDOW_ID = '_solution_window_id';
var NEW_ROOM_WINDOW_ID = '_new_room_window_id';
var NEW_TEACHER_WINDOW_ID = '_new_teacher_window_id';
var SOLUTIONS_BLOCK_ID = '_solutions_block';
var LESSON_WINDOW_LESSON_ID_HIDDEN = '_lesson_id_lesson_window_hidden';

var SOLUTION_SELECT_TEACHER_ID = '_edit_solution_select_teacher';
var SOLUTION_SELECT_ROOM_ID = '_edit_solution_select_room';

var SOLUTION_SOLUTION_ID_HIDDEN = '_solution_solution_id_hidden';
var SOLUTION_LESSON_ID_HIDDEN = '_solution_lesson_id_hidden';

var TEACHER_LASTNAME_ID = 'teacher_lastname';
var TEACHER_FIRSTNAME_ID = 'teacher_firstname';
var TEACHER_MIDDLENAME_ID = 'teacher_middlename';
var TEACHER_POST_ID = 'teacher_post';
var ROOM_NAME = '_roomname';

var LESSON_BACKGROUND = 'lesson_window_background';

var _CTX = '';//'Automatization_netbeans_/';

//уровень окна для контроля нажатий кнопок
//если 0 - окон нет
//1 - открыто окно учебного плана
//2 - открыто окно решения
//3 - открыто окно добавления преподавателя/аудитории
var curent_window_level = 0;

function getFilter(){
   var filter = new Object();
   var spItem = document.getElementById('filter_speciality');
   filter.spec_id = spItem.options[spItem.selectedIndex].value;
   var semItem = document.getElementById('filter_semester');
   filter.sem_id = semItem.options[semItem.selectedIndex].value;
   var chItem = document.getElementById('filter_pchair');
   filter.prchair_id = chItem.options[chItem.selectedIndex].value;
   var discItem = document.getElementById('filter_disc');
   filter.disc_id = discItem.options[discItem.selectedIndex].value;
   var dtItem = document.getElementById('filter_dtype');
   filter.dt_id = dtItem.options[dtItem.selectedIndex].value;
   return filter;
}
function resetFilter(){
   var spItem = document.getElementById('filter_speciality');
   spItem.selectedIndex = 0;
   var semItem = document.getElementById('filter_semester');
   semItem.selectedIndex = 0;
   var chItem = document.getElementById('filter_pchair');
   chItem.selectedIndex = 0;
}
function applyFilter(){
   var filter = getFilter();
   $.ajax({
       url: _CTX + 'lesson/getFilteredPlans',
       type: 'POST',
       async: false,
       data: filter
   }).done(function(data){
       var item = document.getElementById('plans_holder');
       item.innerHTML = data;
   }).fail(function(){
       alert('ajax error!');
   });
}
function viewLesson(id){
    if (curent_window_level > 0){
        return;
    }
    var window = document.getElementById(LESSON_WINDOW_ID);
    if (window !== undefined && window !== null){
        return;
    }
    window = document.createElement('div');
    window.setAttribute('id', LESSON_WINDOW_ID);
    window.setAttribute('class', 'lesson_window');
    $.ajax({
        url: _CTX + 'lesson/viewLesson',
        method: 'POST',
        async: false,
        data: { id : id }
    }).done(function(response){
        var backgr = createLessonBackground();
        if (backgr === null){
            alert('ошибка(background)');
            return;
        }    
        window.innerHTML = response;
        curent_window_level = 1;
        document.body.appendChild(backgr);
        document.body.appendChild(window);
    }).fail(function(){
        alert('Ошибка загрузки учебного плана (ajax)');
    });
   
}
//подгрузит список решений с сервера
function loadSolutions(lesson_id){
    if (lesson_id === undefined || lesson_id === null){
        return;
    }
    var lesson_window = document.getElementById(LESSON_WINDOW_ID);
    if (lesson_window === undefined || lesson_window === null){
        alert('Не найдено открытое окно учебного плана!');
        return;
    }
    var solutions_block = document.getElementById(SOLUTIONS_BLOCK_ID);
    if (solutions_block === undefined || solutions_block === null){
        alert('Не найден контейнер решений в окне учебного плана!');
        return;
    }
    $.ajax({
       url: _CTX + 'lesson/getSolutions',
       type: 'POST',
       async: false,
       data: {lesson_id: lesson_id}
    }).done(function(response){
        solutions_block.innerHTML = response;
    }).fail(function(){
        alert('ошибка при подгрузке решений(ajax)');
    });
}

//покажет окно редактирования решения
function viewSolution(id){
    if (curent_window_level !== 1){
        return;
    }
    var lesson_window = document.getElementById(LESSON_WINDOW_ID);
    if (lesson_window === undefined || lesson_window === null){
        alert('не найдено окно с учебным планом');
        return;
    }
    var lesson_id_hidden = document.getElementById(LESSON_WINDOW_LESSON_ID_HIDDEN);
    if (lesson_id_hidden === undefined || lesson_id_hidden === null){
        alert('не найден id учебного плана');
        return;
    }
    //var solution_id_hidden = document.getElementById(SOLUTION_SOLUTION_ID_HIDDEN);
    var data = new Object();
    data.lesson_id = lesson_id_hidden.value;
//    if (solution_id_hidden !== undefined && solution_id_hidden !== null){
//        data.solution_id = solution_id_hidden.value;
//    }
    if (id !== undefined && id !== null){
        data.solution_id = id;
    }
    
    $.ajax({
        url: _CTX + 'lesson/viewSolution',
        type: 'POST',
        async: false,
        data: data
    }).done(function(response){
        var window = document.createElement('div');
        window.setAttribute('id', SOLUTION_WINDOW_ID);
        window.setAttribute('class', 'solution_window');
        window.innerHTML = response;
        document.body.appendChild(window);
        curent_window_level++;
    }).fail(function(){
        alert('ошибка при получении решения (ajax');
    });

}
function saveSolution(){
  if (curent_window_level !== 2){
      return;
  }
  //для создания/редактирования плана необходимо знать id учебного плана
  var lesson_item = document.getElementById(SOLUTION_LESSON_ID_HIDDEN);
  if (lesson_item === undefined || lesson_item === null){
      alert('ошибка, не найден id учебного плана');
      return;
  }  
  var lesson_id = document.getElementById(SOLUTION_LESSON_ID_HIDDEN).value;
  //если имеется id решения значит это редактирование
  var solution_id = '';
  var solution_item = document.getElementById(SOLUTION_SOLUTION_ID_HIDDEN);
  if (solution_item !== undefined && solution_item !== null){
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

  $.ajax({
      url: _CTX + 'lesson/saveSolution',
      type: 'POST',
      async: false,
      data: data
  }).done(function(response){
      if (response.success){
          //обновить список решений в окне учебного плана
          loadSolutions(lesson_id);
          closeWindow(SOLUTION_WINDOW_ID);
      } else {
          alert(response.message);
      }

  }).fail(function(){
     alert('ошибка при сохранении решения(ajax)');
  });

}

function deleteSolution(id){
    if (curent_window_level !== 1){
        return;
    }
    if (id === undefined || id === null){
        return;
    }
    var lesson_id  = document.getElementById(LESSON_WINDOW_LESSON_ID_HIDDEN);
    if (lesson_id === undefined || lesson_id === null){
        alert('не найден id учебного плана');
        return;
    }
    $.ajax({
        url: _CTX + 'lesson/deleteSolution',
        type: 'POST',
        async: false,
        data: {solution_id : id, lesson_id : lesson_id.value}
    }).done(function(response){
        if (response.success){
            loadSolutions(lesson_id.value);
        } else {
            alert(response.message);
        }
    }).fail(function(){
        alert('ошибка при удалении решения(ajax)');
    });
    
}

function viewAddTeacher(){
    if (curent_window_level !== 2){
        return;
    }
    var window = document.createElement('div');
    window.setAttribute('id', NEW_TEACHER_WINDOW_ID);
    window.setAttribute('class', 'new_teacher_window');
    $.ajax({
        url: _CTX + 'lesson/viewAddTeacher',
        type: 'POST'
    }).done(function(response){
        window.innerHTML = response;
        curent_window_level++;
        document.body.appendChild(window);
    }).fail(function(){
        alert('ошибка при отображении окна(ajax)');
    });
}

function saveTeacher(){
    if (curent_window_level !== 3){
        return;
    }
    var data = new Object();
    var last_name = document.getElementById(TEACHER_LASTNAME_ID);
    if (last_name !== undefined && last_name.value !== undefined){
        data.lastName = last_name.value;
    }
    var first_name = document.getElementById(TEACHER_FIRSTNAME_ID);
    if (first_name !== undefined && first_name.value !== undefined){
        data.firstName = first_name.value;
    }
    var middle_name = document.getElementById(TEACHER_MIDDLENAME_ID);
    if (middle_name !== undefined && middle_name.value !== undefined){
        data.middleName = middle_name.value;
    }
    var post = document.getElementById(TEACHER_POST_ID);
    if (post !== undefined && post.value !== undefined){
        data.post = post.value;
    }
    
    $.ajax({
        url: _CTX + 'lesson/saveTeacher',
        type: 'POST',
        async: false,
        data: data
    }).done(function(response){
        if (! response.success){
            alert(response.message);
            return;
        } else {
            loadTeachers();
            closeWindow(NEW_TEACHER_WINDOW_ID);          
        }
    }).fail(function(){
        alert('ошибка при сохранении преподавателя(ajax)');
    });
    
}

function loadRooms(){
    var window = document.getElementById(SOLUTION_WINDOW_ID);
    if (window === undefined || window === null){
        alert('не найдено окно редактирования решения');
        return;
    }
    var rooms_block = document.getElementById(SOLUTION_SELECT_ROOM_ID);
    if (rooms_block === null || rooms_block === undefined){
        alert('не найден элемент со списком аудиторий');
        return;
    }
    $.ajax({
        url: _CTX + 'lesson/loadRooms',
        async: false,
        type: 'POST'
    }).done(function(response){
        rooms_block.innerHTML = response;
    }).fail(function(){
        alert('ошибка при подгрузке аудиторий(ajax)');
    });
    
}

function saveRoom(){
    if (curent_window_level !== 3){
        return;
    }
    var data = new Object();
    var name = document.getElementById(ROOM_NAME);
    
    if (name !== null && name !== undefined){
        data.room = name.value;
    }
    
    $.ajax({
        url: _CTX + 'lesson/saveRoom',
        type: 'POST',
        async: false,
        data: data
    }).done(function(response){
        if (response.success){
            loadRooms();
            closeWindow(NEW_ROOM_WINDOW_ID);
        } else {
            alert(response.message);
            return;
        }
    }).fail(function(){
        alert('ошибка при сохранении(ajax)');
    });
}

function viewAddRoom(){
    if (curent_window_level !== 2){
        return;
    }
    var window = document.createElement('div');
    window.setAttribute('id', NEW_ROOM_WINDOW_ID);
    window.setAttribute('class', 'new_room_window');
    $.ajax({
        url: _CTX + 'lesson/viewAddRoom',
        async: false,
        type: 'POST'
    }).done(function(response){
        window.innerHTML = response;
        curent_window_level++;
        document.body.appendChild(window);
    }).fail(function(){
        alert('ошибка при отображении окна(ajax)');
    });
}

function loadTeachers(){
    var window = document.getElementById(SOLUTION_WINDOW_ID);
    if (window === undefined || window === null){
        alert('не найдено окно редактирования решения');
        return;
    }
    var teachers_block = document.getElementById(SOLUTION_SELECT_TEACHER_ID);
    if (teachers_block === undefined || teachers_block === null){
        alert('не найден элемент со списком преподавателей');
    }
    
    $.ajax({
        url: _CTX + 'lesson/loadTeachers',
        async: false,
        type: 'POST'
    }).done(function(response){
        teachers_block.innerHTML = response;
    }).fail(function(){
        alert('ошибка при обновлении списка преподавателей(ajax)');
    });
    
}

//закрыть окно и уменьшить уровень открытого окна
function closeWindow(id) {
   if (id === LESSON_WINDOW_ID && curent_window_level !== 1){       
       return;
   }
   if (id === SOLUTION_WINDOW_ID && curent_window_level !== 2){
       return;
   }
   if (id === NEW_TEACHER_WINDOW_ID && curent_window_level !== 3){
       return;
   }
   if (id === NEW_ROOM_WINDOW_ID && curent_window_level !== 3){
       return;
   }
   var window = document.getElementById(id);
   if (window !== undefined && window !== null){
       document.body.removeChild(window);
       if (id === LESSON_WINDOW_ID){
            removeLessonBackground();
       }
       curent_window_level--;
   }    
}

function createLessonBackground(){
    var wraper = document.getElementById('wraper');
    if (wraper === undefined || wraper === null){
        return null;
    }
    //wraper.setAttribute('style', 'overflow:hidden;height:100%;');
    var background = document.createElement('div');
    background.setAttribute('id', LESSON_BACKGROUND);
    return background;
}

function removeLessonBackground(){
    var wraper = document.getElementById('wraper');
    if (wraper === undefined || wraper === null){
        return;
    }
    //wraper.setAttribute('style', '');
    var background = document.getElementById(LESSON_BACKGROUND);
    if (background === null || background === undefined){
        return;
    }
    document.body.removeChild(background);
}