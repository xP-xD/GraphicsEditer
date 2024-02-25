package contents;

public class Contesnts {
	
	public enum EMainFrame {

		Width(1000),
		Height(700);

		private int num;

		private EMainFrame(int num) {
			this.num = num;
		}

		public int getNum() {
			return this.num;
		}

	}
	
	public enum EFileMenu {

		New("New");


		private String name;

		private EFileMenu(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

	}
	
	public enum EMenuBar {

		File("File");


		private String name;

		private EMenuBar(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

	}

//	public enum EGToolbar {
//
//		eSelect("Select", new GSelect(), null);
//
//
//		private String name;
//
//		private EGToolbar(String name) {
//			this.name = name;
//		}
//
//		public String getName() {
//			return this.name;
//		}
//
//	}
	
}
