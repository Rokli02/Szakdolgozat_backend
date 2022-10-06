import { Column, Entity, PrimaryGeneratedColumn } from 'typeorm';

@Entity()
export class Status {
  @PrimaryGeneratedColumn()
    id?: number;

    @Column({ nullable: false })
    name: string;
}