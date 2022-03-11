$('select[data-value]').each(function(index, el) {
  const $el = $(el);
  
  const defaultValue = $el.attr('data-value').trim();
  
  if ( defaultValue.length > 0 ) {
	$el.val(defaultValue);	
  }
});