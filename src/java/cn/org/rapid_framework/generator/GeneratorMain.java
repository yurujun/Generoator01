package cn.org.rapid_framework.generator;


/**
 * 
 * @author badqiu
 * @email badqiu(a)gmail.com
 */

public class GeneratorMain {
	/**
	 * ��ֱ���޸����´�����ò�ͬ�ķ�����ִ�������������.
	 */
	public static void main(String[] args) throws Exception {
		GeneratorFacade g = new GeneratorFacade();
//		g.printAllTableNames();				//��ӡ���ݿ��еı�����
		
		g.deleteOutRootDir();							//ɾ�������������Ŀ¼
//		g.generateByTable("table_name","template");	//ͨ�����ݿ�������ļ�,templateΪģ��ĸ�Ŀ¼
		//g.generateByAllTable("template");	//�Զ��������ݿ��е����б������ļ�,templateΪģ��ĸ�Ŀ¼
//		g.generateByClass(Blog.class,"template_clazz");
		 
//		g.deleteByTable("table_name", "template"); //ɾ�����ɵ��ļ�
		//���ļ���
		Runtime.getRuntime().exec("cmd.exe /c start "+GeneratorProperties.getRequiredProperty("outRoot"));
	}
}
