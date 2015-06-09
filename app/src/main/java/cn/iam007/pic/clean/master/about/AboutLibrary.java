package cn.iam007.pic.clean.master.about;

public class AboutLibrary implements Comparable<AboutLibrary> {
    /*
    <?xml version="1.0" encoding="utf-8"?>
    <resources>

    <string name="define_AboutLibraries">year;owner</string>
    <string name="library_AboutLibraries_author">Mike Penz</string>
    <string name="library_AboutLibraries_authorWebsite">http://mikepenz.com/</string>
    <string name="library_AboutLibraries_libraryName">AboutLibraries</string>
    <string name="library_AboutLibraries_libraryDescription">
    <![CDATA[
    <b>AboutLibraries</b> is a library to offer you all the information you need of your libraries!
    <br /><br />
    Most modern apps feature an "Used Library"-Section and for this some information of those libs is required. As it gets annoying to copy those strings always to your app I have developed this small helper library to provide the required information.
            ]]>
    </string>
    <string name="library_AboutLibraries_libraryVersion">4.7.2</string>
    <string name="library_AboutLibraries_libraryWebsite">https://github.com/mikepenz/AboutLibraries</string>
    <string name="library_AboutLibraries_licenseId">apache_2_0</string>
    <string name="library_AboutLibraries_isOpenSource">true</string>
    <string name="library_AboutLibraries_repositoryLink">https://github.com/mikepenz/AboutLibraries</string>
    <!-- Custom variables section -->
    <string name="library_AboutLibraries_owner">Mike Penz</string>
    <string name="library_AboutLibraries_year">2014</string>
    </resources>
    */

    private String definedName = "";

    // ����
    private String author = "";

    // ���ߵ���ַ
    private String authorWebsite = "";

    // ���ÿ������
    private String libraryName = "";

    // ���ÿ������
    private String libraryDescription = "";

    // ���ÿ�İ汾
    private String libraryVersion = "";

    // ���ÿ�ĵ�ַ
    private String libraryWebsite = "";

    // �Ƿ��ǿ�Դ��
    private boolean isOpenSource = true;

    // ��Դ��ĵ�ַ
    private String repositoryLink = "";

    /**
     * ����AboutLibrary����
     *
     * @param author             ʹ�ÿ������
     * @param libraryName        ʹ�ÿ������
     * @param libraryDescription ʹ�ÿ������
     */
    public AboutLibrary(String author, String libraryName, String libraryDescription) {
        this.author = author;
        this.libraryName = libraryName;
        this.libraryDescription = libraryDescription;
    }

    public String getDefinedName() {
        return definedName;
    }

    public void setDefinedName(String definedName) {
        this.definedName = definedName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorWebsite() {
        return authorWebsite;
    }

    public void setAuthorWebsite(String authorWebsite) {
        this.authorWebsite = authorWebsite;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getLibraryDescription() {
        return libraryDescription;
    }

    public void setLibraryDescription(String libraryDescription) {
        this.libraryDescription = libraryDescription;
    }

    public String getLibraryVersion() {
        return libraryVersion;
    }

    public void setLibraryVersion(String libraryVersion) {
        this.libraryVersion = libraryVersion;
    }

    public String getLibraryWebsite() {
        return libraryWebsite;
    }

    public void setLibraryWebsite(String libraryWebsite) {
        this.libraryWebsite = libraryWebsite;
    }

    public boolean isOpenSource() {
        return isOpenSource;
    }

    public void setOpenSource(boolean isOpenSource) {
        this.isOpenSource = isOpenSource;
    }

    public String getRepositoryLink() {
        return repositoryLink;
    }

    public void setRepositoryLink(String repositoryLink) {
        this.repositoryLink = repositoryLink;
    }

    @Override
    public int compareTo(AboutLibrary another) {
        return getLibraryName().compareToIgnoreCase(another.getLibraryName());
    }
}
