module btx.prog.two.fs22.project {
	requires transitive javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires org.apache.commons.text;
	requires com.google.gson;

	exports btx.prog.project.domain;
	exports btx.prog.project.cli;
	exports btx.prog.project.gui to javafx.graphics;

	opens btx.prog.project.gui to javafx.fxml;
	opens btx.prog.project.domain;
}
