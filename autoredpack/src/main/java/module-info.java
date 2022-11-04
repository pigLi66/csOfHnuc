module org.dahanjsy.autoredpack {
    exports org.dahanjsy.autoredpack;
    exports org.dahanjsy.autoredpack.views;
    exports org.dahanjsy.autoredpack.service;
    exports org.dahanjsy.autoredpack.service.model;
    exports org.dahanjsy.autoredpack.service.handler;
    exports org.dahanjsy.autoredpack.common.utils;
    exports org.dahanjsy.autoredpack.common;

    requires kotlin.stdlib;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires hanlp.portable;
}