/*-----------------------------------------------------------------------------------*/
$(".menu-toggle").on('click', function() {
  $(this).toggleClass("on");
  $('.menu-section').toggleClass("on");
  $("nav ul").toggleClass('hidden');
});	

//main menu end

/*-----------------------------------------------------------------------------------*/

// wow aimtion

new WOW().init();

//Wow animation End

/*-----------------------------------------------------------------------------------*/
(function titleScroller(text) {
        document.title = text;
        console.log(text);
        setTimeout(function () {
            titleScroller(text.substr(1) + text.substr(0, 1));
        }, 300);
    }("Food Inspection - Dubai  Police  "));
