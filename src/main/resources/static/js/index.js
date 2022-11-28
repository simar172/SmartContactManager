//$(".sidebar").css("display", "none");//
//$(".ctnt").css("margin-left", "10px");-->//
const toggler = () => {
  if ($(".sidebar").is(":visible")) {
    $(".sidebar").css("display", "none");
    $(".ctnt").css("margin-left", "10px");
  } else {
    $(".sidebar").css("display", "block");
    $(".ctnt").css("margin-left", "20%");
  }
};
document.querySelector(".name").addEventListener('change',(e)=>{
  console.log(e.target.value);
})
