package com.example.demo.services;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Topic;
import com.example.demo.repositories.InformationRepository;
import com.example.demo.repositories.TopicRepository;

import jakarta.annotation.PostConstruct;

@Service
public class TopicService {

	@Autowired
	private TopicRepository topicRepository;
	
	@Autowired
	private InformationRepository informationRepository;
	
	public void AddTopic(Topic topic)
	{
		topicRepository.save(topic);
	}
	
	public Topic GetTopic(int ID)
	{
		return topicRepository.getById(ID);
	}
	
	public boolean CheckExist()
	{
		return topicRepository.findAll().size() > 0 ? true : false;
	}
	
	public List<Topic> getAllTopics() 
	{
		return topicRepository.findAll().stream().sorted((t1,t2) -> (t2.getUsers().size() > t1.getUsers().size() ? 1 : 0)).toList();
	}
	
	public List<Topic> getAllUnfavourateTopics(int infoID)
	{
		List<Topic> topics = new ArrayList<>(getAllTopics());
		//System.out.println(topics.get(0).hashCode());
		for (Topic p : getAllFavoriteTopics(infoID))
		{
			//System.out.println(p.hashCode());
			topics.remove(GetTopic(p.getTopicID()));
		}
		return topics;
	}
	
//	public List<Topic> getAllUnfavourateTopics(int infoID) {
//	    List<Topic> topics = new ArrayList<Topic>(getAllTopics()); // Tạo một bản sao của danh sách để xử lý.
//	    List<Topic> favoriteTopics = getAllFavoriteTopics(infoID);
//	    
//	    Iterator<Topic> iterator = topics.iterator();
//	    while (iterator.hasNext()) {
//	        Topic currentTopic = iterator.next();
//	        for (Topic favTopic : favoriteTopics) {
//	            if (currentTopic.getTopicID() == favTopic.getTopicID()) {
//	                iterator.remove(); // Xóa nếu là một topic yêu thích
//	                break; // Ngừng kiểm tra các topic yêu thích còn lại
//	            }
//	        }
//	    }
//	    return topics;
//	}

	
	public List<Topic> getAllFavoriteTopics(int infoID)
	{
		return informationRepository.getById(infoID).getTopics();
	}
	
	@PostConstruct
	private void SettingUpTopic() {
		
		if (CheckExist() == true) return;
		
		AddTopic(new Topic( "IELTS Writing", "https://media.istockphoto.com/id/1141968788/vector/concept-of-literary-art-with-letters-coming-out-of-a-pencil.jpg?s=612x612&w=0&k=20&c=30p9CxNFBNH-A7Xt-or1Nbz9RVfaqrLnRJFza9k16V4="));
		AddTopic(new Topic( "IELTS Speaking", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSXnf6i9anIkxNSCBW-xNYiXZSklsIz4hyr_QIYfbfVQ&s"));
		AddTopic(new Topic( "IELTS Reading", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS52pIsv3iYd5F12byQnsO-slsbN5bI-kWjlhHtXU7GmQ&s"));
		AddTopic(new Topic( "IELTS Listening", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRyAqSwTfSl7z1wsZGHGB8fbclj1s1DYMdbMDAdrd1sUA&s"));
		AddTopic(new Topic( "TOEIC", "https://tiki.vn/blog/wp-content/uploads/2023/07/toeic.jpg"));
		AddTopic(new Topic( "Toán 12", "https://t4.ftcdn.net/jpg/04/61/65/03/360_F_461650383_vOTkFxYQ2T2kvuymieHDHbIWjghyL3DY.jpg"));
		AddTopic(new Topic( "Toán 11", "https://i.pinimg.com/564x/7d/16/4d/7d164d6d2c4947322886ba92447b47ba.jpg"));
		AddTopic(new Topic( "Toán 10", "https://img.pikbest.com/png-images/cartoon-math-clipart-math-clipart_5858103.png!bw700"));
		AddTopic(new Topic( "Văn 12", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSKZzHSn7n8xN1IcJuAZXxQFAGm2cJKwFML88QrPnmr2w&s"));
		AddTopic(new Topic( "Văn 11", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSX1wdcjeak8TFPYtO-jPk_79hCJhelkKtffqj-2TlSTQAZ92RZoc-u7DSnG-cVTk_8rJo&usqp=CAU"));
		AddTopic(new Topic( "Văn 10", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTIqH63H06agNGag5OLKrwUmtRgGHk-bq0WzpFHvxfA8nBksbcEPqfnFccCDZ3bGqNpJEY&usqp=CAU"));
		AddTopic(new Topic( "Hóa 12", "https://t4.ftcdn.net/jpg/05/52/90/05/360_F_552900530_D4WF1c3zGsvIGfLgKaRBrEmbvPlk6O6E.jpg"));
		AddTopic(new Topic( "Hóa 11", "https://png.pngtree.com/png-clipart/20190515/original/pngtree-student-chemistry-class-experiment-test-tube-png-image_3838894.jpg"));
		AddTopic(new Topic( "Hóa 10", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSm84rwoFN81sw0wurwlMvLKN26hvrzqSP8BqCtpQyttQ&s"));
		AddTopic(new Topic( "Lý 12", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTVVGRZJPWwH-KJNEY40IEBLvDY-TAgZ24qRKCjae1PQQ&s"));
		AddTopic(new Topic( "Lý 11", "https://img.freepik.com/free-vector/gravity-ball-swinging-left-right_1308-74405.jpg?size=626&ext=jpg&ga=GA1.1.2008272138.1712880000&semt=ais"));
		AddTopic(new Topic( "Lý 10", "https://st4.depositphotos.com/1000423/22262/i/450/depositphotos_222624382-stock-photo-humankind-and-science.jpg"));
		AddTopic(new Topic( "Sinh 12", "https://media.istockphoto.com/id/450853983/photo/dna-molecules.jpg?s=612x612&w=0&k=20&c=ARYPd6zjC9_PhxTh4T6b6WFL7hCgHm5FJdpsTVW543U="));
		AddTopic(new Topic( "Sinh 11", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTlcKenXeyVWYNIyjXYsK4miuQ-ZgRjfCtLjWZKzHCReQ&s"));
		AddTopic(new Topic( "Sinh 10", "https://cdn1.vectorstock.com/i/1000x1000/28/50/biology-science-design-vector-8842850.jpg"));
		AddTopic(new Topic( "IT", "https://c8.alamy.com/comp/H3NA29/it-consultant-presenting-tag-cloud-about-information-technology-H3NA29.jpg"));
		AddTopic(new Topic( "Algorithms", "https://st2.depositphotos.com/1579454/9602/i/450/depositphotos_96020736-stock-photo-flow-chart-diagram.jpg"));
		AddTopic(new Topic( "Advanced Algorithms", "https://media.istockphoto.com/id/610566912/photo/flow-chart-diagram.jpg?s=612x612&w=0&k=20&c=pyQTD56ou5GYrYZugtS2EpvPGsobLLBcMSzLX6_jhdo="));
		AddTopic(new Topic( "Networking", "https://www.shutterstock.com/image-photo/business-woman-drawing-global-structure-260nw-1006041130.jpg"));
		AddTopic(new Topic( "Hardware", "https://knowledge.wharton.upenn.edu/wp-content/uploads/2019/03/032519_hosonagarbook_algorithms-900x387.jpg"));
		AddTopic(new Topic( "Software", "https://img.freepik.com/free-vector/isometric-cms-concept_23-2148807389.jpg?size=626&ext=jpg&ga=GA1.1.1700460183.1712880000&semt=ais"));
		AddTopic(new Topic( "Java programming", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLimXLRzG3GfrfqIGMqDWaXBEk2g91-5HB9dwGhV4EwA&s"));
		AddTopic(new Topic( "C# programming", "https://www.simplilearn.com/ice9/free_resources_article_thumb/Csharp_Programming_for_Beginners.jpg"));
		AddTopic(new Topic( "Javascript programming", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcodCRTx1YoozfJJJH1NY2aB72d5252P2FxhGEhjRcoQ&s"));
		AddTopic(new Topic( "Golang programming", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR2ykMK616kk41X02BnRwJWK65sAU9qs4o8BcS2RzF2UA&s"));
		AddTopic(new Topic( "Python programming", "https://media.geeksforgeeks.org/wp-content/uploads/20240304152903/python-tutorial-2.webp"));
		AddTopic(new Topic( "Backend", "https://c8.alamy.com/comp/2DB193X/back-end-icon-simple-element-from-website-development-collection-filled-back-end-icon-for-templates-infographics-and-more-2DB193X.jpg"));
		AddTopic(new Topic( "Frontend", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRy-6boMV0C0sLsx7EQtRCjXPAKXfJpN5oEjUWsG5dvXQ&s"));
		AddTopic(new Topic( "Fullstack", "https://www.revenueriver.co/hubfs/fullstack.jpeg"));
		AddTopic(new Topic( "Devops", "https://marvel-b1-cdn.bc0a.com/f00000000236551/dt-cdn.net/wp-content/uploads/2021/07/13429_ILL_DevOpsLoop.png"));
		AddTopic(new Topic( "Tester", "https://topdev.vn/blog/wp-content/uploads/2020/09/tester-la-gi-1.jpg"));
		AddTopic(new Topic( "Security", "https://www.nokia.com/sites/default/files/2022-01/cybersecurity4.jpg"));
		AddTopic(new Topic( "Kinh tế vĩ mô", "https://navi.com/blog/wp-content/uploads/2022/12/Planned-Economy.jpg"));
		AddTopic(new Topic( "Kinh tế vi mô", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ_F12cpWYKEtnFLRQaRGetiT2-NTWuB0Sfp7AIqZJ4Dg&s"));
		AddTopic(new Topic( "Toán kinh tế", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR4zs9L6YaMr7pQyV561Qh5fPwX-4cNuEGA1CgjXwd7Fw&s"));
		AddTopic(new Topic( "Trí tuệ nhân tạo", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQjMwjb6ISprjY6koK2_-dhtFpgtkjYTgHn8NpsYPAmeQ&s"));
		AddTopic(new Topic( "Quản lí tài chính", "https://cdn5.vectorstock.com/i/1000x1000/47/14/upward-chart-personal-finance-management-concept-vector-21374714.jpg"));
		AddTopic(new Topic( "Quản lí nhân sự", "https://online.maryville.edu/wp-content/uploads/sites/97/2023/09/MVU-BSHR-2020-Q2-Skyscraper-What-is-Human-Resource-Management-header-v2.jpg"));
		AddTopic(new Topic( "Marketing", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSmPzJIzQu5bS6CmjYRbwogkIJAiqPZ70ooWPBUKgIeww&s"));
		AddTopic(new Topic( "Social Media", "https://bigthink.com/wp-content/uploads/2022/03/AdobeStock_389328016_Editorial_Use_Only.jpeg"));
		AddTopic(new Topic( "Data analysis", "https://cdn-icons-png.freepik.com/512/6897/6897881.png"));
		AddTopic(new Topic( "Database design", "https://bs-uploads.toptal.io/blackfish-uploads/components/seo/5923698/og_image/optimized/0712-Bad_Practices_in_Database_Design_-_Are_You_Making_These_Mistakes_Dan_Social-754bc73011e057dc76e55a44a954e0c3.png"));
		AddTopic(new Topic( "Xác xuất thống kê", "https://img-c.udemycdn.com/course/750x422/1847126_d557_7.jpg"));
		AddTopic(new Topic( "Đại số tuyến tính", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Linear_subspaces_with_shading.svg/800px-Linear_subspaces_with_shading.svg.png"));
		AddTopic(new Topic( "Toán cao cấp 1", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSyetQif4BRmkp1DhfFYf09MgbX0QVj6M22iH1SUBjKMQ&s"));
		AddTopic(new Topic( "Toán cao cấp 2", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQh-42SZ0YAYN3kw8xdwH6fgLSc8PKIlAQfJcNMXN9MdA&s"));
		AddTopic(new Topic( "Toán cao cấp 3", "https://www.matrix.edu.au/wp-content/uploads/2020/07/guide-12-Maths-Adv-hero-banner.jpg"));
		AddTopic(new Topic( "Toán rời rạc", "https://thumbs.dreamstime.com/z/girl-s-head-education-idea-close-up-blonde-woman-standing-near-gray-wall-looking-upwards-drawing-brain-98253352.jpg"));
		AddTopic(new Topic( "Hệ điều hành", "https://cdn.hswstatic.com/gif/computer-operating-sytem.jpg"));
		AddTopic(new Topic( "Quản trị, vận hành và phát triển doanh nghiệp", "https://www.highview.com.au/wp/wp-content/uploads/2021/09/RevOps-image.png"));
		AddTopic(new Topic( "Triết học", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR3_mlPjvPHOa25luZR3thU7pwn5x5QlnfH6m9yz54dCA&s"));
		AddTopic(new Topic( "Lịch sử việt nam", "https://www.mrlinhadventure.com/UserFiles/image/Photos%20Nord%20Vietnam/sapa/Sapa(1).jpg"));
		AddTopic(new Topic( "Lịch sử thế giới", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTwMiaruIStwdLazJpdHki5m6NjhGAkl5ceAee0La4XOQ&s"));
		AddTopic(new Topic( "Đầu tư chứng khoán", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTJnMVp8f2gNDM9L6F81iSWD1kq6GkCd_6AGvC22h8j8Q&s"));
		AddTopic(new Topic( "Đầu tư tiền ảo", "https://bitcoinist.com/wp-content/uploads/2023/12/Crypto-events.png"));
		AddTopic(new Topic( "Tiếng Trung", "https://www.shutterstock.com/image-vector/vector-illustration-two-hand-drawn-260nw-751101376.jpg"));
		AddTopic(new Topic( "Tiếng Hàn", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTA4V8eJ-01feqLIwVBwnWDIln9cV4m3NhADbi5jSEg8Q&s"));
		AddTopic(new Topic( "Tiếng Nhật", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTdcJ_vOSTYlmymItk0m5iqMseACJjgdv-dPJgIiC0pag&s"));
		AddTopic(new Topic( "Tiếng Thái", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQUt7cpUkRCSatsJh43JmASPZvV7W8fYsN-e3_IA1k3Ug&s"));
		AddTopic(new Topic( "Tiếng Pháp", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSqwgD1K4cXhSuZLWwMtguWSCthT2A6og6RJNiTwfFvLw&s"));
		AddTopic(new Topic( "Tiếng Đức", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNxvUKAfQcMsQIkVuHX8BYOMaBFEtJtymQsFUWmLOThQ&s"));
		AddTopic(new Topic( "Tiếng Ý", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRNEs3KIhelmC02FnK8rjY8etw2EbDel94vYyGofIcSOA&s"));
		AddTopic(new Topic( "Tiếng Bồ Đào Nha", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWIuPT7yoTVl0TYTB6qGShd-4uKsq9zbd5Qu005S6z-cDVkSiTtUifItzoFOmwRRf52To&usqp=CAU"));
		AddTopic(new Topic( "Chăm sóc sức khỏe", "https://www.thetimes.co.uk/imageserver/image/%2Fmethode%2Ftimes%2Fprod%2Fweb%2Fbin%2F049f79b0-ef5d-11ed-b02d-cefaa3091195.jpg?crop=1600%2C900%2C0%2C0&resize=1200"));
		AddTopic(new Topic( "Chăm sóc thú cưng", "https://www.netmeds.com/images/cms/wysiwyg/blog/2023/03/1680092089_Pet-Care_898x450.jpg"));
		AddTopic(new Topic( "Gym", "https://img.freepik.com/free-photo/3d-gym-equipment_23-2151114163.jpg?size=626&ext=jpg&ga=GA1.1.1700460183.1712707200&semt=ais"));
		AddTopic(new Topic( "Bóng đá", "https://cdn.britannica.com/51/190751-131-B431C216/soccer-ball-goal.jpg"));
		AddTopic(new Topic( "Bóng bàn", "https://custom-images.strikinglycdn.com/res/hrscywv4p/image/upload/c_limit,fl_lossy,h_9000,w_1200,f_auto,q_auto/1503259/440035_638564.jpeg"));
		AddTopic(new Topic( "Bóng rổ", "https://ssww-blog.s3.amazonaws.com/blog/wp-content/uploads/basketball-buying-guide.jpg"));
		AddTopic(new Topic( "Bóng chày", "https://img.freepik.com/free-vector/gradient-baseball-background_23-2150755865.jpg"));
		AddTopic(new Topic( "Bóng chuyền", "https://media.istockphoto.com/id/1371823675/photo/bad-shot.jpg?s=612x612&w=0&k=20&c=JK8hNxPDZ1CQKLHCm17-KrLrb0KOcT3D5jbzYtkk40c="));
		AddTopic(new Topic( "Điền kinh", "https://www.shutterstock.com/image-photo/sport-runner-on-start-260nw-567905164.jpg"));
		AddTopic(new Topic( "Bơi lội", "https://img.freepik.com/free-vector/teenage-boy-swimming-blue-water_1308-132882.jpg"));
		AddTopic(new Topic( "Nhảy xa", "https://media.post.rvohealth.io/wp-content/uploads/2020/05/Jump_Fitness_Female_Leap-732x549-Thumbnail.jpg"));
		AddTopic(new Topic( "Cầu lông", "https://as1.ftcdn.net/v2/jpg/03/10/62/12/1000_F_310621281_foEqKBGtGlNWFQRePgdF5BpLOFyTsnzO.jpg"));
		AddTopic(new Topic( "Tennis", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSYm2b-kQZnTq2d2vHMJsECxhrMdylZ0jYCvS472WzGQ&s"));
		AddTopic(new Topic( "Thiên văn học", "https://img.freepik.com/free-photo/galaxy-glowing-starfield-illuminates-dark-night-sky-generated-by-ai_188544-15600.jpg?size=626&ext=jpg&ga=GA1.1.1700460183.1712880000&semt=sph"));
		AddTopic(new Topic( "Khảo cổ học", "https://www.socsci.ox.ac.uk/sites/default/files/socsci/images/page/1._griffiths_arch_community_archaeology.jpg"));
		AddTopic(new Topic( "Kĩ thuật ô tô", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRLdMKNORuTVDLwoFRR1zpvs_voab3IL3X6uMEICu0ffg&s"));
		AddTopic(new Topic( "Kĩ thuật dệt may", "https://www.emta.org.uk/wp-content/uploads/2021/07/emta-textile-engineering.jpg"));
		AddTopic(new Topic( "Kỹ thuật hóa học", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcScnyTSLPOv9nSAEylAaxEko35Z74csXFIFVefBfptGgw&s"));
		AddTopic(new Topic( "Kỹ thuật tự động hóa", "https://builtin.com/sites/www.builtin.com/files/styles/course_card/public/2022-01/21_6.jpg"));
		AddTopic(new Topic( "Kỹ thuật cơ điện lạnh", "https://dienlanhthaigia.com/wp-content/uploads/co-dien-lanh-1.jpg"));
		AddTopic(new Topic( "Kỹ thuật điện lực", "https://electricalengineeringmagazine.co.uk/wp-content/uploads/321233/shutterstock_1709519638.500x0-is.jpg"));
		AddTopic(new Topic( "Kĩ thuật điện tử", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTuGRIP5yMTcrxVu8T7YlI84XU2v5UncPeCmaRWwxfudw&s"));
		AddTopic(new Topic( "Piano", "https://t4.ftcdn.net/jpg/02/57/33/93/360_F_257339302_LWVM6ZkukZUoVVo8CY0UHx5y283PG9RR.jpg"));
		AddTopic(new Topic( "Guitar", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6eHwt7qm57P-ZFUiF_3ahQg4kGpBb6CaTRMwPFIX-4Q&s"));
		AddTopic(new Topic( "Violin", "https://t3.ftcdn.net/jpg/00/10/72/88/360_F_10728874_7aVGyJqe4Zx3W9HKTRbbVPznuR8CrAdp.jpg"));
		AddTopic(new Topic( "Kèn", "https://img.freepik.com/free-vector/trumpet-white-background_1308-98485.jpg?size=626&ext=jpg&ga=GA1.1.1700460183.1712707200&semt=ais"));
		AddTopic(new Topic( "Sáo", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ3cn08XNfx3m409hsViEJvkq2y8265QWK7dxSoxPFf6w&s"));
		AddTopic(new Topic( "Trống", "https://media.istockphoto.com/id/1148943461/vector/beautiful-wooden-percussion-musical-instrument-drum-with-chopsticks.jpg?s=612x612&w=0&k=20&c=zG7BnUTK87LIkFAl_L_8nitiN1AWxwz3GUamV94dcXw="));
		AddTopic(new Topic( "Đàn tranh", "https://photo-cms-vovworld.zadn.vn/w500/uploaded/vovworld/asfzyrvslys/2016_06_03/dan%20tranh_copy.jpg"));
		AddTopic(new Topic( "Đàn bầu", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSkE1EvzO0MQgH64wTJbSpNPwV_yLcnTEtgPnLTpOas_g&s"));
		AddTopic(new Topic( "Cơ sở dữ liệu", "https://blog.teamnexus.in/blog/wp-content/uploads/2022/06/databases.png"));
		AddTopic(new Topic( "Quản lí dự án mã nguồn - GIT", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHk50P-gWtS96gcvLUElNqGUbrH38TqzOpn8YJdlp9EQ&s"));
	}
}
