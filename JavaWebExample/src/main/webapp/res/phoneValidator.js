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

    var dateInput = form.querySelector('input[name="birthdate"]');
            var now = new Date();

    function validateDate() {
        var curDate = new Date(dateInput.value);
        if (curDate.getFullYear() < 1900) {
            dateInput.setCustomValidity("Year must be > 1900");
        } else if (curDate.getFullYear() > now.getFullYear())
        {
            dateInput.setCustomValidity("Year must be <= this year");
        } else {
            dateInput.setCustomValidity("");
        }
    }

    form.addEventListener('submit', validateDate);
    dateInput.addEventListener('change', validateDate);
}
document.addEventListener('DOMContentLoaded', initForm);