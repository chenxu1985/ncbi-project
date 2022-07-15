import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Project {

    public static void main(String[] args) {

//        String projectFilePath = "/Users/laphael/Desktop/ncbi/untitled.xml";
        String projectFilePath = "/Users/laphael/Desktop/ncbi/bioproject_PRJNA784038.xml";
        String outPath = "/Users/laphael/Desktop/ncbi/bioproject_PRJNA784038.sql";
//        System.out.println(projectFilePath);
        File file = new File(projectFilePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            File toFile = new File(outPath);
            toFile.createNewFile();
            FileWriter toWriter = new FileWriter(toFile);
            BufferedWriter toOut = new BufferedWriter(toWriter);
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            NodeList proPackage = doc.getElementsByTagName("Project");
            int grant_id = 1;
            for (int i = 0; i < proPackage.getLength(); i++) {
                Element packE = (Element) proPackage.item(i);
                    NodeList accessionList= packE.getElementsByTagName("ArchiveID");
                    Element e = (Element) accessionList.item(0);
                    String accession =e.getAttribute("accession");

                    NodeList descrList= packE.getElementsByTagName("ProjectDescr");
                    Element descNode = (Element)descrList.item(0);

                    NodeList prj_title_list = descNode.getElementsByTagName("Title");
                    Element titleE = (Element) prj_title_list.item(0);
                    String title = "";
                    if(titleE!=null){
                        title = titleE.getFirstChild().getNodeValue();
                    } else {
                        title = "N/A";
                    }
                    //Relevance
                    NodeList relevance_list = descNode.getElementsByTagName("Relevance");
                    String relevance = "";
                    for(int rel=0;rel<relevance_list.getLength();rel++){
                        Element relNode =  (Element) relevance_list.item(rel);
                        NodeList agriculturalList = relNode.getElementsByTagName("Agricultural");
                        Element agriculturalE = (Element) agriculturalList.item(0);
                        if(agriculturalE!=null){
                            if(!relevance.equals("")){
                                relevance = relevance +","+ "Agricultural";
                            } else {
                                relevance = relevance + "Agricultural";
                            }
                        }
                        NodeList medicalList = relNode.getElementsByTagName("Medical");
                        Element medicalE = (Element) medicalList.item(0);
                        if(medicalE!=null){
                            if(!relevance.equals("")){
                                relevance = relevance +","+ "Medical";
                            } else {
                                relevance = relevance + "Medical";
                            }
                        }
                        NodeList industrialList = relNode.getElementsByTagName("Industrial");
                        Element industrialE = (Element) industrialList.item(0);
                        if(industrialE!=null){
                            if(!relevance.equals("")){
                                relevance = relevance +","+ "Industrial";
                            } else {
                                relevance = relevance + "Industrial";
                            }
                        }
                        NodeList environmentalList = relNode.getElementsByTagName("Environmental");
                        Element environmentalE = (Element) environmentalList.item(0);
                        if(environmentalE!=null){
                            if(!relevance.equals("")){
                                relevance = relevance +","+ "Environmental";
                            } else {
                                relevance = relevance + "Environmental";
                            }
                        }
                        NodeList evolutionList = relNode.getElementsByTagName("Evolution");
                        Element evolutionE = (Element) evolutionList.item(0);
                        if(evolutionE!=null){
                            if(!relevance.equals("")){
                                relevance = relevance +","+ "Evolution";
                            } else {
                                relevance = relevance + "Evolution";
                            }
                        }
                        NodeList modelOrganismList = relNode.getElementsByTagName("ModelOrganism");
                        Element modelOrganismE = (Element) modelOrganismList.item(0);
                        if(modelOrganismE!=null){
                            if(!relevance.equals("")){
                                relevance = relevance +","+ "ModelOrganism";
                            } else {
                                relevance = relevance + "ModelOrganism";
                            }
                        }
                        NodeList otherList = relNode.getElementsByTagName("Other");
                        Element otherE = (Element) otherList.item(0);
                        if(otherE!=null){
                            if(!relevance.equals("")){
                                relevance = relevance +","+ otherE.getFirstChild().getNodeValue();
                            } else {
                                relevance = relevance + otherE.getFirstChild().getNodeValue();
                            }
                        }
                    }

                    //project_desc
                    NodeList prj_desc_list = descNode.getElementsByTagName("Description");
                    Element descE = (Element) prj_desc_list.item(0);
                    String desc = "";
                    if(descE!=null){
                        desc = descE.getFirstChild().getNodeValue();
                    }
                    //Grants
                    NodeList grant_list = descNode.getElementsByTagName("Grant");
                    for(int gra=0;gra<grant_list.getLength();gra++){
                        Element graNode =  (Element) grant_list.item(gra);
                        String grantId =graNode.getAttribute("GrantId");
                        NodeList titleList = graNode.getElementsByTagName("Title");
                        Element grantTitleE = (Element) titleList.item(0);
                        String grantTitle = "";
                        if(grantTitleE!=null){
                            grantTitle = grantTitleE.getFirstChild().getNodeValue();
                        }
                        NodeList agencyList = graNode.getElementsByTagName("Agency");
                        Element agencyE = (Element) agencyList.item(0);
                        String agency_abbr =agencyE.getAttribute("abbr");
                        String agency = "";
                        if(agencyE!=null){
                            agency = agencyE.getFirstChild().getNodeValue();
                        }
                        String insertPrjGrants = "insert into prj_grants(grants_id,agency,agency_abbr,grant_ID,grant_title,prj_second_accession) values("+grant_id+",";


                        if(agency.endsWith("\\")){
                            agency = agency.substring(0,agency.length()-1);
                        }
                        if(agency.contains("\"")){
                            if(agency.contains("'")){
                                agency = agency.replace("'","\\'");
                                insertPrjGrants = insertPrjGrants + "'"+agency+"',";
                            } else {
                                insertPrjGrants = insertPrjGrants + "'"+agency+"',";
                            }
                        } else {
                            insertPrjGrants = insertPrjGrants + "\""+agency+"\",";
                        }

                        if(agency_abbr.endsWith("\\")){
                            agency_abbr = agency_abbr.substring(0,agency_abbr.length()-1);
                        }
                        if(agency_abbr.contains("\"")){
                            if(agency_abbr.contains("'")){
                                agency_abbr = agency_abbr.replace("'","\\'");
                                insertPrjGrants = insertPrjGrants + "'"+agency_abbr+"'," ;

                            } else {
                                insertPrjGrants = insertPrjGrants + "'"+agency_abbr+"'," ;
                            }
                        } else {
                            insertPrjGrants = insertPrjGrants + "\""+agency_abbr+"\",";
                        }

                        if(grantId.contains("\"")){
                            if(grantId.contains("'")){
                                agency_abbr = grantId.replace("'","\\'");
                                insertPrjGrants = insertPrjGrants + "'"+grantId+"',";
                            } else {
                                insertPrjGrants = insertPrjGrants +  "'"+grantId+"',";
                            }
                        } else {
                            insertPrjGrants = insertPrjGrants +  "\""+grantId+"\",";
                        }


                        if(grantTitle.contains("\"")){
                            if(grantTitle.contains("'")){
                                grantTitle = grantTitle.replace("'","\\'");
                                insertPrjGrants = insertPrjGrants + "'"+grantTitle+"','"+accession+"');";
                            } else {
                                insertPrjGrants = insertPrjGrants + "'"+grantTitle+"','"+accession+"');";
                            }
                        } else {
                            insertPrjGrants = insertPrjGrants + "\""+grantTitle+"\",'"+accession+"');";
                        }
                        System.out.println(insertPrjGrants);
                        toOut.write(insertPrjGrants+"\n");
                        String insertProGrant = "insert into pro_grants(grants_id,prj_second_accession) values("+grant_id+",'"+accession+"');";
                        System.out.println(insertProGrant);
                        toOut.write(insertProGrant+"\n");
                        grant_id++;
                    }

                    NodeList external_Link_list = descNode.getElementsByTagName("ExternalLink");
                    for(int ell=0;ell<external_Link_list.getLength();ell++){
                        Element linkNode =  (Element) external_Link_list.item(ell);
                        String category =linkNode.getAttribute("category");
                        String label =linkNode.getAttribute("label");

                        NodeList urlList = linkNode.getElementsByTagName("URL");
                        Element urlE = (Element) urlList.item(0);
                        String url = "";
                        if(urlE!=null){
                            url = urlE.getFirstChild().getNodeValue();

                        }
                        String insertExternalLink = "";
                        url = url.replace("'s","\\'s");
                        if(url.contains("\"")){
                            if(url.contains("'")&&!url.contains("'s")){
                                url = url.replace("'","\\'");
                                insertExternalLink = "insert into prj_external_link(external_link_url,description,prj_second_accession) values('"+url+"',";
                            } else {
                                insertExternalLink = "insert into prj_external_link(external_link_url,description,prj_second_accession) values('"+url+"',";
                            }
                        } else {
                            insertExternalLink = "insert into prj_external_link(external_link_url,description,prj_second_accession) values(\""+url+"\",";
                        }

                        if(label.contains("\"")){
                            if(label.contains("'")){
                                label = label.replace("'","\\'");
                                insertExternalLink = insertExternalLink + "'"+label+"','"+accession+"');";
                            } else {
                                insertExternalLink = insertExternalLink + "'"+label+"','"+accession+"');";
                            }
                        } else {
                            insertExternalLink = insertExternalLink + "\""+label+"\",'"+accession+"');";
                        }
                        System.out.println(insertExternalLink);
                        toOut.write(insertExternalLink+"\n");
                    }


                    //Accessions in other database
                    NodeList secondaryList= packE.getElementsByTagName("SecondaryArchiveID");
                    Element secondaryNode =  (Element) secondaryList.item(0);
                    if(secondaryNode!=null){
                        for(int se=0;se<secondaryList.getLength();se++){
                            Element seNode =  (Element) secondaryList.item(se);
                            String seAccession =seNode.getAttribute("accession");
                            String dbName=seNode.getAttribute("archive");
//                            System.out.println(seAccession);
//                            System.out.println(dbName);
                            String insertRefSql = "insert into prj_crossdb_ref(ref_db_name,ref_accession,prj_second_accession) values('"+dbName+"','"+seAccession+"','"+accession+"');";
                            System.out.println(insertRefSql);
                            toOut.write(insertRefSql+"\n");
                        }
                    }

                    //project_data_type

                        NodeList IntendedList= packE.getElementsByTagName("ProjectDataTypeSet");
                    Element IntendedE = (Element) IntendedList.item(0);
                    int data_type_id=0;
                    if(IntendedE!=null){
                        NodeList projectTypeList= IntendedE.getElementsByTagName("DataType");
                        Element projectTypeE = (Element) projectTypeList.item(0);
                        String projectType =projectTypeE.getFirstChild().getNodeValue();
//                        String prjDateTypeSql = "";
                        if(projectType.toLowerCase().equals("genome sequencing")){
                            data_type_id = 30;
                        } else if(projectType.toLowerCase().equals("raw sequence reads")){
                            data_type_id = 29;
                        }else if(projectType.toLowerCase().equals("genome sequencing and assembly")){
                            data_type_id = 28;
                        }else if(projectType.toLowerCase().equals("metagenome")){
                            data_type_id = 6;
                        }else if(projectType.toLowerCase().equals("metagenomic assembly")){
                            data_type_id = 32;
                        }else if(projectType.toLowerCase().equals("assembly")){
                            data_type_id = 31;
                        }else if(projectType.toLowerCase().equals("transcriptome")){
                            data_type_id = 10;
                        }else if(projectType.toLowerCase().equals("proteomic")){
                            //此ID为插入数据库固定值other暂定为1
                            data_type_id = 14;
                        }else if(projectType.toLowerCase().equals("map")){
                            data_type_id = 5;
                        }else if(projectType.toLowerCase().equals("clone ends")){
                            data_type_id = 2;
                        }else if(projectType.toLowerCase().equals("targeted loci")||projectType.equals("Targeted Locus (Loci)")){
                            data_type_id = 9;
                        }else if(projectType.toLowerCase().equals("targeted loci cultured")){
                            data_type_id = 33;
                        }else if(projectType.toLowerCase().equals("targeted loci environmental")){
                            data_type_id = 34;
                        }else if(projectType.toLowerCase().equals("random survey")){
                            data_type_id = 8;
                        }else if(projectType.toLowerCase().equals("exome")){
                            data_type_id = 4;
                        }else if(projectType.toLowerCase().equals("variation")){
                            data_type_id = 11;
                        }else if(projectType.toLowerCase().equals("epigenomics")){
                            data_type_id = 3;
                        }else if(projectType.toLowerCase().equals("phenotype or genotype")){
                            data_type_id = 7;
                        }else if(projectType.toLowerCase().equals("transcriptome or gene expression")){
                            data_type_id = 12;
                        }else if(projectType.toLowerCase().equals("refSeq genome")){
                            data_type_id = 13;
                        }else if(projectType.toLowerCase().equals("refSeq genome sequencing and assembly")){
                            data_type_id = 15;
                        }else if(projectType.toLowerCase().equals("refSeq genome sequencing")){
                            data_type_id = 16;
                        }else if(projectType.toLowerCase().equals("proteome")) {
                            data_type_id = 17;
                        }else if(projectType.toLowerCase().equals("refSeq raw sequence reads")){
                            data_type_id = 18;
                        }else if(projectType.toLowerCase().equals("refSeq other")){
                            data_type_id = 19;
                        }else if(projectType.toLowerCase().equals("refSeq variation")){
                            data_type_id = 20;
                        }else if(projectType.equals("RefSeq Targeted Locus (Loci)")){
                            data_type_id = 21;
                        }else if(projectType.toLowerCase().equals("refSeq assembly")){
                            data_type_id = 22;
                        }else if(projectType.toLowerCase().equals("refSeq transcriptome")){
                            data_type_id = 23;
                        }else if(projectType.toLowerCase().equals("refSeq targeted loci")){
                            data_type_id = 24;
                        }else if(projectType.toLowerCase().equals("refSeq phenotype or genotype")){
                            data_type_id = 25;
                        }else if(projectType.toLowerCase().equals("refSeq map")){
                            data_type_id = 26;
                        }else if(projectType.toLowerCase().equals("refSeq metagenomic assembly")){
                            data_type_id = 27;
                        } else if(projectType.toLowerCase().equals("other")){
                            data_type_id = 35;
                        } else {
                            data_type_id = 35;
                            //other
                        }
                    } else {
                        //设定一个miss
                        data_type_id=35;
                    }
                    //sample_scope
                    NodeList sampleScopeList= packE.getElementsByTagName("Target");
                    Element sampleScopee = (Element) sampleScopeList.item(0);
                    String sample_scope =sampleScopee.getAttribute("sample_scope");
                    int sample_scope_id = 0;
//                    String prjSampleScopeSql = "";

                    if(sample_scope.equals("eMonoisolate")){
                        sample_scope_id = 1;
                    } else if(sample_scope.equals("eMultiisolate")){
                        sample_scope_id = 2;
                    } else if(sample_scope.equals("eMultispecies")){
                        sample_scope_id = 3;
                    } else if(sample_scope.equals("eEnvironment")){
                        sample_scope_id = 4;
                    } else if(sample_scope.equals("eSynthetic")){
                        sample_scope_id = 5;
                    } else if(sample_scope.equals("eSingleCell")){
                        sample_scope_id = 6;
                    } else if(sample_scope.equals("eOther")){
                        //prjSampleScopeSql = "insert into prj_sample_scope(sample_scope_name,is_other,prj_accession) values('other',1,'"+accession+"');";
                        sample_scope_id = 7;
                    }
//                    System.out.println(prjSampleScopeSql);
                    //Publication
                    NodeList publication_list = descNode.getElementsByTagName("Publication");
                    for(int pul=0;pul<publication_list.getLength();pul++){

                        Element pubListNode =  (Element) publication_list.item(pul);
                        String id = pubListNode.getAttribute("id");
                        //dbType
                        NodeList dbTypeList = pubListNode.getElementsByTagName("DbType");
                        Element dbTypeE = (Element) dbTypeList.item(0);
                        String dbType = dbTypeE.getFirstChild().getNodeValue();
//                        System.out.println("DbType="+dbType);
                        String doi = "";
                        String pmd = "";
                        if(dbType.equals("ePubmed")){
                            pmd = id;
                        } else if(dbType.equals("eDOI")){
                            doi = id;
                        }
                        //article_title
                        NodeList pub_title_List = pubListNode.getElementsByTagName("Title");
                        Element pub_titleE = (Element) pub_title_List.item(0);
                        String pub_title = "";
                        if(pub_titleE!=null){
                            pub_title = pub_titleE.getFirstChild().getNodeValue();
//                            System.out.println(pub_title);
                        }
                        //journal_title
                        NodeList journal_title_List = pubListNode.getElementsByTagName("JournalTitle");
                        Element journal_titleE = (Element) journal_title_List.item(0);
                        String journal_title = "";
                        if(journal_titleE!=null){
                            journal_title = journal_titleE.getFirstChild().getNodeValue();
//                            System.out.println(journal_title);
                        }
                        //year
                        NodeList year_List = pubListNode.getElementsByTagName("Year");
                        Element yearE = (Element) year_List.item(0);
                        String year = "";
                        if(yearE!=null){
                            year = yearE.getFirstChild().getNodeValue();
//                            System.out.println(year);
                        }
                        //volume
                        NodeList volume_List = pubListNode.getElementsByTagName("Volume");
                        Element volumeE = (Element) volume_List.item(0);
                        String volume = "";
                        if(volumeE!=null){
                            volume = volumeE.getFirstChild().getNodeValue();
//                            System.out.println(volume);
                        }
                        //issue
                        NodeList issue_List = pubListNode.getElementsByTagName("Issue");
                        Element issueE = (Element) issue_List.item(0);
                        String issue = "";
                        if(issueE!=null){
                            issue = issueE.getFirstChild().getNodeValue();
//                            System.out.println(issue);
                        }
                        //PagesFrom
                        NodeList pagesfrom_List = pubListNode.getElementsByTagName("PagesFrom");
                        Element pagesfromE = (Element) pagesfrom_List.item(0);
                        String pagesfrom = "";
                        if(pagesfromE!=null){
                             pagesfrom = pagesfromE.getFirstChild().getNodeValue();
//                            System.out.println(pagesfrom);
                        }
                        //PagesTo
                        NodeList pagesto_List = pubListNode.getElementsByTagName("PagesTo");
                        Element pagestoE = (Element) pagesto_List.item(0);
                        String pagesto = "";
                        if(pagestoE!=null){
                              pagesto = pagestoE.getFirstChild().getNodeValue();
//                            System.out.println(pagesto);
                        }
                        //author
                        NodeList author_List = pubListNode.getElementsByTagName("Author");
                        Element authorNode =  (Element) author_List.item(0);
                        String authorList= "";
                        if(authorNode!=null){
                            for(int author=0;author<author_List.getLength();author++){
                                Element authorListNode =  (Element) author_List.item(author);
                                NodeList lastList = authorListNode.getElementsByTagName("Last");
                                Element lastE = (Element) lastList.item(0);
                                String Last = "";
                                if(lastE!=null){
                                    Last = lastE.getFirstChild().getNodeValue();
                                }
//                                System.out.println(Last);
                                NodeList firstList = authorListNode.getElementsByTagName("First");
                                String first = "";
                                Element firstE = (Element) firstList.item(0);
                                if(firstE!=null){
                                    first = firstE.getFirstChild().getNodeValue();
                                }
                                if(author==0){
                                    authorList = Last + " " + first;
                                } else {
                                    authorList = authorList + ","+ Last + " " + first;
                                }

                            }
                        }
//                        System.out.println("authorList="+authorList);
//                        String insertPublicationSql = "insert into publication(pubmed_id,doi,journal_title,article_title,year,volume,issue,pagefrom,pageto,author_list,prj_accession,prj_second_accession) ";
                        String insertPublicationSql = "insert into publication(pubmed_id,doi,journal_title,article_title,year,volume,issue,pagefrom,pageto,author_list,prj_second_accession) ";
                        pub_title = pub_title.replace("'s","\\'s");
                        if(pub_title.contains("\"")){
                            if(pub_title.contains("'")&&!pub_title.contains("'s")){
                                pub_title = pub_title.replace("'","\\'");
//                                insertPublicationSql = insertPublicationSql + "values('"+pmd+"','"+doi+"',\""+journal_title+"\",'"+pub_title+"','"+year+"','"+volume+"','"+issue+"','"+pagesfrom+"','"+pagesto+"',\""+authorList+"\",'"+newGsaAccession+"','"+accession+"');";
                                insertPublicationSql = insertPublicationSql + "values('"+pmd+"','"+doi+"',\""+journal_title+"\",'"+pub_title+"','"+year+"','"+volume+"','"+issue+"','"+pagesfrom+"','"+pagesto+"',\""+authorList+"\",'"+accession+"');";
                            } else {
//                                insertPublicationSql = insertPublicationSql + "values('"+pmd+"','"+doi+"',\""+journal_title+"\",'"+pub_title+"','"+year+"','"+volume+"','"+issue+"','"+pagesfrom+"','"+pagesto+"',\""+authorList+"\",'"+newGsaAccession+"','"+accession+"');";
                                insertPublicationSql = insertPublicationSql + "values('"+pmd+"','"+doi+"',\""+journal_title+"\",'"+pub_title+"','"+year+"','"+volume+"','"+issue+"','"+pagesfrom+"','"+pagesto+"',\""+authorList+"\",'"+accession+"');";
                            }
                        } else {
//                            insertPublicationSql = insertPublicationSql + "values('"+pmd+"','"+doi+"',\""+journal_title+"\",\""+pub_title+"\",'"+year+"','"+volume+"','"+issue+"','"+pagesfrom+"','"+pagesto+"',\""+authorList+"\",'"+newGsaAccession+"','"+accession+"');";
                            insertPublicationSql = insertPublicationSql + "values('"+pmd+"','"+doi+"',\""+journal_title+"\",\""+pub_title+"\",'"+year+"','"+volume+"','"+issue+"','"+pagesfrom+"','"+pagesto+"',\""+authorList+"\",'"+accession+"');";
                        }

                        System.out.println(insertPublicationSql);
                        toOut.write(insertPublicationSql+"\n");
                    }

                    //ProjectReleaseDate
                    NodeList releaseList= packE.getElementsByTagName("ProjectReleaseDate");
                    Element releaseE = (Element) releaseList.item(0);
                    String release = "";
                    int release_flag = 1;
                    if(releaseE!=null) {
                        release =releaseE.getFirstChild().getNodeValue();
                        release =release.substring(0,10);
                    } else {
                        //release = "2015-01-01";
                        release_flag = 0;
                    }
//                    System.out.println("release="+release+release_flag);


                NodeList subPackage = doc.getElementsByTagName("Submission");
                Element subE = (Element) subPackage.item(0);

                    String createTime =subE.getAttribute("submitted");
                    int create_flag = 1;
                    if(createTime==null||createTime.equals("")){
                        createTime = "2015-01-01";
                        create_flag = 0;
                    }
                    NodeList orgList= subE.getElementsByTagName("Organization");
                    Element  orge =  (Element) orgList.item(0);
                    String organization = "";
                    if(orge!=null){
                        for(int org=0;org<orgList.getLength();org++){
                            Element orgeNode =  (Element) orgList.item(org);
                            NodeList nameList = orgeNode.getElementsByTagName("Name");
                            Element nameE = (Element) nameList.item(0);
                            String name = nameE.getFirstChild().getNodeValue();
                            if(org==0){
                                organization = name;
                            } else {
                                organization = organization + "|"+ name;
                            }

                        }
                    } else {
                        organization = "NCBI";
                    }
                    if(organization.endsWith("\\")){
                        organization = organization.substring(0,organization.length()-1);
                    }

                    //物种
                    String organism = "{";
                    NodeList org = packE.getElementsByTagName("Organism");
                    for(int or=0;or<org.getLength();or++){
                        Element orgNode =  (Element) org.item(or);
                        String taxID =orgNode.getAttribute("taxID");
                        String name = "";
                        NodeList nameN = orgNode.getElementsByTagName("OrganismName");
                        Element nameE = (Element) nameN.item(0);
                        if(nameE!=null){
                            if(nameE.getFirstChild()!=null){
                                name = nameE.getFirstChild().getNodeValue();
                            }
                        }
                        if(taxID.contains("\n")){
                            taxID = taxID.replace("\n","");
                        }
                        if(taxID.contains("\\n")){
                            taxID = taxID.replace("\\n","\\\\n");
                        }
                        if(taxID.contains("\t")){
                            taxID = taxID.replace("\t","");
                        }
                        if(taxID.contains("#")){
                            taxID = taxID.replace("#","\\#");
                        }
                        if(taxID.contains("&quot")){
                            taxID = taxID.replace("&quot","");
                        }
                        if(taxID.contains("\"")){
                            taxID = taxID.replace("\"","");
                            if(taxID.contains("'")){
                                taxID = taxID.replace("'","\\'");
                            }
                            taxID =  "\""+taxID+"\"";
                        } else {
                            if(taxID.contains("'")){
                                taxID = taxID.replace("'","\\'");
                            }
                            taxID = "\""+taxID+"\"";
                        }

//                        if(value.contains("'s")){
//                            value = value.replace("'s","\\'s");
//                        }
                        if(name.contains("\n")){
                            name = name.replace("\n","");
                        }
                        if(name.contains("\\n")&&!name.contains("\\\\n")){
                            name = name.replace("\\n","\\\\n");
                        }
                        if(name.contains("\t")){
                            name = name.replace("\t","");
                        }
                        if(name.contains("#")){
                            name = name.replace("#","\\#");
                        }
                        if(name.contains("\\")){
                            name = checkString(name);
                        }
                        if(name.contains("&quot")){
                            name = name.replace("&quot","");
                        }
                        if(name.contains("\"")){
                            name = name.replace("\"","");
                            if(name.contains("'")){
                                name = name.replace("'","\\'");
//                                value = "'"+value+"'";
                            }
                            name = "\""+name+"\"";
                        } else {
                            if(name.contains("'")){
                                name = name.replace("'","\\'");
                            }
                            name = "\""+name+"\"";
                        }
                        if(or==0) {
                            organism = organism + taxID +":"+name;
                        } else {
                            organism = organism + "," + taxID +":"+name;
                        }
                    }
                    organism =organism +"}";
                    //submitter
//                    String insertSubmitterSql = "insert into project_submitter(first_name,last_name,email,organization,department,street,city,postal_code,country_id,prj_accession)";
//                    organization = organization.replace("'s","\\'s");
//                    if(organization.contains("\"")){
//                        if(organization.contains("'")&&!organization.contains("'s")){
//                            organization = organization.replace("'","\\'");
//                            insertSubmitterSql = insertSubmitterSql +  " values('GSA','GSA','gsa-submit@big.ac.cn','"+organization+"','National Genomics Data Center','No.1 Beichen West Road, Chaoyang District','Beijing','100101',45,'"+accession+"');";
//                        } else {
//                            insertSubmitterSql = insertSubmitterSql +  " values('GSA','GSA','gsa-submit@big.ac.cn','"+organization+"','National Genomics Data Center','No.1 Beichen West Road, Chaoyang District','Beijing','100101',45,'"+accession+"');";
//                        }
//                    } else {
//                        insertSubmitterSql = insertSubmitterSql +  " values('GSA','GSA','gsa-submit@big.ac.cn',\""+organization+"\",'National Genomics Data Center','No.1 Beichen West Road, Chaoyang District','Beijing','100101',45,'"+accession+"');";
//                    }
//                    System.out.println(insertSubmitterSql);
//                    String insertSql = "insert into project(accession,second_accession,sample_scope_id,data_type_id,user_id,create_time,release_time,status,title,description,relevance,release_state,release_flag,create_flag)";
                    String insertSql ="";
                    if(organism.equals("{}")){
                        insertSql = "insert into project(second_accession,sample_scope_id,data_type_id,user_id,create_time,release_time,status,title,description,relevance,release_state,release_flag,create_flag,organization)";
                    } else {
                        insertSql = "insert into project(second_accession,sample_scope_id,data_type_id,user_id,create_time,release_time,status,title,description,relevance,release_state,release_flag,create_flag,organization,organism)";
                    }
//                    title = title.replace("'s","\\'s");
                    if(title.contains("\"")){
                        if(title.contains("'")){
                            title = title.replace("'","\\'");
//                            insertSql = insertSql +" values('"+newGsaAccession+"','"+accession+"',"+sample_scope_id+","+data_type_id+",2930,'"+createTime+"','"+release+"',3,'"+title+"',";
                            insertSql = insertSql +" values('"+accession+"',"+sample_scope_id+","+data_type_id+",2930,'"+createTime+"','"+release+"',3,'"+title+"',";
                        } else {
//                            insertSql = insertSql +" values('"+newGsaAccession+"','"+accession+"',"+sample_scope_id+","+data_type_id+",2930,'"+createTime+"','"+release+"',3,'"+title+"',";
                            insertSql = insertSql +" values('"+accession+"',"+sample_scope_id+","+data_type_id+",2930,'"+createTime+"','"+release+"',3,'"+title+"',";
                        }
                    } else {
//                        insertSql = insertSql +" values('"+newGsaAccession+"','"+accession+"',"+sample_scope_id+","+data_type_id+",2930,'"+createTime+"','"+release+"',3,\""+title+"\",";
                        insertSql = insertSql +" values('"+accession+"',"+sample_scope_id+","+data_type_id+",2930,'"+createTime+"','"+release+"',3,\""+title+"\",";
                    }

                    if(desc.endsWith("\\")){
                        desc = desc.substring(0,desc.length()-1);
                    }
                    if(desc.contains("\"")){
                        if(desc.contains("'")){
                            desc = desc.replace("'","\\'");
                            insertSql = insertSql +"'"+desc+"',";
                        } else {
                            insertSql = insertSql +"'"+desc+"'," ;
                        }
                    } else {
                        insertSql = insertSql +"\""+desc+"\"," ;
                    }

                    if(relevance.contains("\"")){
                        if(relevance.contains("'")){
                            relevance = relevance.replace("'","\\'");
                            insertSql = insertSql +"'"+relevance+"',2,"+release_flag+","+create_flag+",";
                        } else {
                            insertSql = insertSql +"'"+relevance+"',2,"+release_flag+","+create_flag+",";
                        }
                    } else {
                        insertSql = insertSql +"\""+relevance+"\",2,"+release_flag+","+create_flag+",";
                    }
                    if(organism.equals("{}")){
                        organization = organization.replace("'s","\\'s");
                        if(organization.contains("\"")){
                            if(organization.contains("'")&&!organization.contains("'s")){
                                organization = organization.replace("'","\\'");
                                insertSql = insertSql + "'"+organization+"');";
                            } else {
                                insertSql = insertSql + "'"+organization+"');";
                            }
                        } else {
                            insertSql = insertSql + "\""+organization+"\");";
                        }
                    } else {
                        organization = organization.replace("'s","\\'s");
                        if(organization.contains("\"")){
                            if(organization.contains("'")&&!organization.contains("'s")){
                                organization = organization.replace("'","\\'");
                                insertSql = insertSql + "'"+organization+"','"+organism+"');";
                            } else {
                                insertSql = insertSql + "'"+organization+"','"+organism+"');";
                            }
                        } else {
                            insertSql = insertSql + "\""+organization+"\",'"+organism+"');";
                        }
                    }

                    System.out.println(insertSql);
                    toOut.write(insertSql+"\n");

            }
            toOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String addPrjAcc(String gsa_accession) {
        String prefix = gsa_accession.substring(0, 4);
        char alphaCode = gsa_accession.charAt(4);
        String numCode = gsa_accession.substring(5);
        int num = Integer.parseInt(numCode) + 1;
        if (num == 1000000) {
            if (alphaCode == 'Z') {
                System.out.println("数值查过设定范围");
            } else {
                alphaCode += 1;
                numCode = "000000";
            }

        } else {
            numCode = numCode.substring(0, 6 - String.valueOf(num).length())
                    + num;
        }
        String newAccession = prefix + alphaCode + numCode;
        return newAccession;
    }
    public static String checkString(String value) {
        String re = "";
        if(value.contains("\\b")){
            value = value.replace("\\b","\\\\b");
        } else if(value.contains("\\r")&&!value.contains("\\\\r")){
            value = value.replace("\\r","\\\\r");
        }else if(value.contains("\\%")){
            value = value.replace("\\%","%");
        }else if(value.contains("\\0")){
            value = value.replace("\\0","0");
        } else if(value.contains("\\\\")){
            value = value.replace("\\\\","\\\\\\\\");
        }
        re = value;
        return re;
    }
}
