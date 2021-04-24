package com.oak.member.entities;

import com.oak.member.enums.InvoiceTitleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * @author wuxp
 */
//@Table(name = "t_member_invoice_title",
//        indexes = {
//                @Index(name = "index_member_invoice_title_member_id", columnList = "member_id")
//
//        })
//@Entity
@Schema(description = "用户发票抬头表")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class MemberInvoiceTitle implements java.io.Serializable {

    private static final long serialVersionUID = -6850105865547390490L;
    @Schema(description = "id")
    @Id
    @GeneratedValue()
    private Long id;

    @Schema(description = "会员ID")
    @Column(name = "member_id", nullable = false)
    private Long memberId;

//    @Schema(description = "发票类型")
//    @Column(name = "typ", nullable = false)
//    private InvoiceType type;

    @Schema(description = "发票类型")
    @Column(name = "title_type", nullable = false, length = 16)
    private InvoiceTitleType titleType;

    @Schema(description = "发票抬头[普通发票]")
    @Column(name = "title", nullable = false, length = 64)
    private String title;

    @Schema(description = "单位名称")
    @Column(name = "company")
    private String company;

    @Schema(description = "纳税人识别号")
    @Column(name = "company_code", length = 32)
    private String companyCode;

    @Schema(description = "注册地址")
    @Column(name = "register_address")
    private String registerAddress;

    @Schema(description = "注册电话")
    @Column(name = "register_phone", length = 12)
    private String registerPhone;

    @Schema(description = "开户银行")
    @Column(name = "open_blank")
    private String openBlank;

    @Schema(description = "银行帐户")
    @Column(name = "blank_account", length = 32)
    private String blankAccount;

    @Schema(description = "收票人姓名")
    @Column(name = "receiver_name", length = 20)
    private String receiverName;

    @Schema(description = "收票人手机号")
    @Column(name = "receiver_phone", length = 15)
    private String receiverPhone;

    @Schema(description = "收票人邮箱")
    @Column(name = "receiver_e_mail", length = 32)
    private String receiverEmail;


}
