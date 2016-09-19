package cn.yanf.Repository;

import cn.yanf.entity.TieBar;
import org.springframework.data.mongodb.repository.MongoRepository;



/**
 * Created by Administrator on 2016/9/18 0018.
 */
public interface TieBarRepository extends MongoRepository<TieBar,String> {
//    public TieBar findByFirstName(String firstName);
//    public List<TieBar> findByLastName(String lastName);
}
