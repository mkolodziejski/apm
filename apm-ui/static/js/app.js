AppController = function() { }

appController = new AppController();

_.extend(AppController.prototype, {
    init: function() {
        this.viewController = new ViewController();

        this.osController = new OSController(this.viewController);
        this.jvmController = new JVMController(this.viewController);
        this.runtimeController = new RuntimeController(this.viewController);


        this.bindMenuEvents();

        // initially select OS
        $('#menu_option_os').click();
    },



    bindMenuEvents: function() {
        var that = this;

        $('#menu_option_os').click(function(e) {
            that.menuElementClicked(e);
            that.currentContentController = that.osController;
            that.currentContentController.load();
        });

        $('#menu_option_jvm').click(function(e) {
            that.menuElementClicked(e);
            that.currentContentController = that.jvmController;
            that.currentContentController.load();
        });

        $('#menu_option_runtime').click(function(e) {
            that.menuElementClicked(e);
            that.currentContentController = that.runtimeController;
            that.currentContentController.load();
        });
    },


    menuElementClicked: function(e) {
        e.preventDefault();

        if(this.currentContentController) {
            this.viewController.clearContent();
            this.currentContentController.unload();
        }

        this.selectMenuElement(e.target);
    },

    selectMenuElement: function(el) {
        $('.menu_option').removeClass('selected');
        $(el).addClass('selected');
    }
});
