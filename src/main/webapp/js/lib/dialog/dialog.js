/**
 * 通用的对话框实现
 * 通常的对话框实现有几种方式，
 * 1.使用浏览器自带的dialog，如IE自带，但是其他浏览器不兼容。
 * 2.直接在页面上写组件。
 *   这种方式，组件往往比较复杂，如果不借助组件库，通常难道很大。
 *   如果把组件单独到独立的js中，通常可以实现公用。
 *   单组件样式等都依赖与类库。通常为了使用一个小的dialog，就必须引入一堆的类库。
 * 本组件采用了另一种方式，一个包含iframe的dialog组件。
 * 这种方式
 * 1.每个dialog都是单独的html文件维护，通用性强。
 * 2.组件可以很复杂，具体的实现，却是普通的页面。可维护性强。
 * 3.随手可用。组件会默认实例化一个对象。随时可以访问dialog对象，使用对话框组件。
 */
function Dialog(){
    this._show=false;
    this._url='';
    this._callback=null;
    this.opt=null;
    this._dialogClass='ai-dialog';
    this._overlayClass='ai-overlay';
    this.default={
        width:400,
        height:300,
        titleHeiht:30,
        title:'对话框'
    };
}
var dialog=new Dialog();

Dialog.prototype._initHtml = function(){
    $('.'+this._dialogClass).remove();
    $('.'+this._overlayClass).remove();

    var dialog='<div class="'+this._dialogClass+'" style="display:none;">'
        +'<div class="title"><span class="lnr lnr-cog"></span><span class="lnr lnr-cross pull-right"></span></div>'
        +'<iframe src="" frameborder="0">'
        +    '<h2>此浏览器不支持iframe</h2>'
        +'</iframe></div>';
    var overlay ='<div class="'+this._overlayClass+'" style="display:none;"></div>';

    $('body').append(overlay).append(dialog);
    //
    var ref=this;
    $('.'+this._dialogClass+' .title .lnr-cross').click(function(){
        ref.close();
    });
}

Dialog.prototype.show = function(url,callback,opt){
    if(this._show){
        return ;
    }

    this._url = url;
    this._callback = callback;
    this._opt= $.extend(this.default,opt);
    
    if($('.'+this._dialogClass).size() <= 0){
        this._initHtml();
    }
    
    //修改对话框显示内容
    $('.'+this._dialogClass).children('.title').children('.lnr-cog').html(this._opt['title']);
    $('.'+this._dialogClass).css('width',this._opt['width']).css('height',this._opt['height'])
    .children('iframe').css('width','100%').css('height',(this._opt['height']-this._opt['titleHeiht'])+'px').attr('src',url);

    //显示
    $('.'+this._dialogClass).show();
    $('.'+this._overlayClass).show();
    this._show=true;
}

Dialog.prototype.close = function(){
    $('.'+this._dialogClass).hide();
    $('.'+this._overlayClass).hide();
    this._show=false;
}

Dialog.prototype.callback=function(){
    this._callback.apply(this,arguments);
    this.close();
}
