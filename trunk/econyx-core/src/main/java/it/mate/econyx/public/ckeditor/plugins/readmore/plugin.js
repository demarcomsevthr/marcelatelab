/**
 * demarcom
 */

CKEDITOR.plugins.add( 'readmore', {
    init: function( editor ) {
       editor.addCommand( 'insertReadmore', { 
           canUndo : true,
           exec: function( editor ) {    
               editor.insertHtml( '<p id="cke-readmore" class="cke-readmore"><img src="/main/ckeditor/plugins/readmore/icons/readmore.gif"/></p>' );
           }
       });
       editor.ui.addButton( 'Readmore', {
           label: 'Insert Readmore tag',
           command: 'insertReadmore',
           icon: '/main/ckeditor/plugins/readmore/icons/readmore-1.png',
           className: 'cke_button_readmore'
       });
   }
});
	

/*
CKEDITOR.plugins.add( 'readmore', {
    icons: 'readmore',
	lang: 'en,it', 
    init: function( editor ) {
       editor.addCommand( 'insertReadmore', { 
           modes : { wysiwyg:1, source:0 }, canUndo : true,
           exec: function( editor ) {    
               editor.insertHtml( '<p id="ckd-readmore" class="ckd-readmore">Read more...</p>' );
           },
           editorFocus : true
       });
       editor.ui.addButton( 'Readmore', {
           label: 'Insert Readmore tag',
           command: 'insertReadmore'
       });
   }
});
*/