package com.bunary.vocab.service.admin.user;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.RoleResDTO;
import com.bunary.vocab.dto.reponse.UserResponseDTO;
import com.bunary.vocab.model.QRole;
import com.bunary.vocab.model.QUser;
import com.bunary.vocab.model.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserAdminService implements IUserAdminService {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<UserResponseDTO> findAll(Map<String, String> params, Pageable pageable) {
        QUser user = QUser.user;
        QRole role = QRole.role;

        // 1. Build filter
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(user.isEmailVerified.eq(true));

        String keyword = params.get("keyword");
        if (keyword != null && !keyword.isEmpty()) {
            builder.and(user.fullName.lower().like("%" + keyword.toLowerCase() + "%"));
        }

        // 2. Dynamic sort
        OrderSpecifier<?> order = user.createdAt.desc();
        String sort = params.get("sort");
        if (sort != null && !sort.isEmpty()) {
            String[] parts = sort.split(",");
            String field = parts[0];
            String dir = parts.length > 1 ? parts[1] : "desc";

            boolean asc = dir.equalsIgnoreCase("asc");
            switch (field.toLowerCase()) {
                case "createdat" -> order = asc ? user.createdAt.asc() : user.createdAt.desc();
                case "fullname" -> order = asc ? user.fullName.asc() : user.fullName.desc();
            }
        }

        // 3. Main query: fetch users with roles
        List<User> users = queryFactory
                .selectFrom(user)
                .leftJoin(user.roles, role).fetchJoin()
                .where(builder)
                .orderBy(order)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .distinct()
                .fetch();

        // 4. Count query (không join roles)
        Long total = queryFactory
                .select(user.countDistinct())
                .from(user)
                .where(builder)
                .fetchOne();

        // 5. Map to DTO
        List<UserResponseDTO> dtos = users.stream()
                .map(u -> {
                    UserResponseDTO dto = new UserResponseDTO();
                    dto.setAvatar(u.getAvatar());
                    dto.setEmail(u.getEmail());
                    dto.setFullName(u.getFullName());
                    dto.setRoles(u.getRoles().stream()
                            .map(r -> new RoleResDTO(r.getId(), r.getName()))
                            .collect(Collectors.toSet()));
                    return dto;
                })
                .toList();

        return new PageImpl<>(dtos, pageable, total);
    }

}
