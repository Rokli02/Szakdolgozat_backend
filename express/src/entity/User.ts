import {Entity, PrimaryGeneratedColumn, Column, ManyToOne, Unique, CreateDateColumn, JoinColumn} from "typeorm";
import { Role } from './Role';

@Entity()
@Unique(['username'])
@Unique(['email'])
export class User {
    @PrimaryGeneratedColumn()
    id: number;

    @Column({ nullable: false })
    name: string;

    @Column({type: 'date', nullable: false})
    birthdate: string;

    @Column({ nullable: false })
    username: string;

    @Column({ nullable: false })
    email: string;

    @Column({ nullable: false })
    password: string;

    @Column({ type: 'boolean', default: true })
    active: boolean;

    @CreateDateColumn()
    created: string;

    @ManyToOne(() => Role, {
        nullable: false,
        eager: true
    })
    @JoinColumn({
        name: "role_id"
    })
    role: Role;
}
