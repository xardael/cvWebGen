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
});