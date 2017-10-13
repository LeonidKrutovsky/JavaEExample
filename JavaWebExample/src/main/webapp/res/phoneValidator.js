function initForm() {
    var form = document.querySelector('#PhonebookForm');
    var tel1 = form.querySelector('input[name="work_phone"]');
    var tel2 = form.querySelector('input[name="mobile_phone"]');

    function validatePhone() {
        if (tel1.value.slice(0, 3) !== tel2.value.slice(0, 3)) {
            tel2.setCustomValidity("Error: first three digits of mobile and work phone must be equal");
        } else {
            tel2.setCustomValidity("");
        }
    }
    form.addEventListener('submit', validatePhone);
    tel1.addEventListener('change', validatePhone);
    tel2.addEventListener('change', validatePhone);
}
document.addEventListener('DOMContentLoaded', initForm);