$(function() {
    $('.template-trigger-add').on('click', function(event) {
        var $trigger = $(this);
        var $section = $trigger.closest('section');
        var $placeholder = $section.find('.placeholder').first();
        var $template = $('.template[data-trigger='+ $trigger.attr('rel') +']').first();
        $placeholder.append($template.html());
        event.preventDefault();
    });

    $('.template-trigger-remove').live('click', function(event) {
        var $trigger = $(this);
        var $fieldset = $trigger.closest('fieldset');
        $fieldset.remove();
        event.preventDefault();
    });

    $('#homepage-create .btn').on('click', function(event) {
        var $trigger = $(this);
        var $form = $trigger.closest('form');
        var $input = $('#homepage-create-email');
        var emailReg = /^([\w\-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;

        if ($input.val() == "" || !emailReg.test($input.val())) {
            alert("Enter valid e-mail address");
            event.preventDefault();
        }


    });
});