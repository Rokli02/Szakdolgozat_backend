import { Column, Entity, JoinColumn, ManyToOne, PrimaryGeneratedColumn, Unique, UpdateDateColumn } from 'typeorm';
import { Series } from './Series';
import { Status } from './Status';
import { User } from './User';

@Entity()
@Unique(['user', 'series'])
export class Userseries {
  @PrimaryGeneratedColumn()
  id: number;

  @ManyToOne(() => User, {
    nullable: false,
    onDelete: 'CASCADE'
  })
  @JoinColumn({
    name: "user_id"
  })
  user: User;

  @ManyToOne(() => Series, {
    nullable: false
  })
  @JoinColumn({
    name: "series_id"
  })
  series: Series;

  @ManyToOne(() => Status, {
    nullable: false
  })
  @JoinColumn({
    name: "status_id"
  })
  status: Status;

  @Column({
    default: 1
  })
  season: number;

  @Column({
    default: 1
  })
  episode: number;

  @UpdateDateColumn()
  modification: string;
}