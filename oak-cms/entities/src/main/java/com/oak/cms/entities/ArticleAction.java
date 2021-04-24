package com.oak.cms.entities;

import com.levin.commons.dao.annotation.Ignore;
import com.oak.cms.enums.ArticleActionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;


@Schema(description = "文章互动记录")
@Entity
@Table(name = "t_cms_article_action",
        uniqueConstraints = {
                @UniqueConstraint(name = "u_constraints_index_cms_article_action",
                        columnNames = {"article_id", "source_code", "action_type"})})
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"article"})
public class ArticleAction implements java.io.Serializable {

    private static final long serialVersionUID = -1295657318705317315L;
    @Schema(description = "ID")
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Schema(description = "文章ID")
    @Column(name = "article_id", nullable = false)
    private Long articleId;

    @Schema(description = "文章信息")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    @Ignore
    private Article article;

    @Schema(description = "互动类型")
    @Column(name = "action_type", nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    private ArticleActionType actionType;

    @Schema(description = "关联来源")
    @Column(name = "source_code", nullable = false, length = 64)
    private String sourceCode;

    @Schema(description = "创建日期")
    @Column(name = "crate_time", length = 19, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date crateTime;

    @Schema(description = "访问的客户端ip")
    @Column(name = "ip")
    private String ip;


}
