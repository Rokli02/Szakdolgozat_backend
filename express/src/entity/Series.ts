import { Column, CreateDateColumn, Entity, JoinColumn, JoinTable, ManyToMany, OneToMany, OneToOne, PrimaryGeneratedColumn } from 'typeorm';
import { Category } from './Category';
import { Image } from './Image';
import { Newsfeed } from './Newsfeed';
import { Season } from './Season';
import { Userseries } from './Userseries';

@Entity()
export class Series {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({ nullable: false })
  title: string;

  @Column({
    type: 'year',
    nullable: false,
    name: "prod_year"
  })
  prodYear: number;

  @Column({
    nullable: false,
    name: "age_limit"
  })
  ageLimit: number;

  @Column({nullable: false})
  length: number; // in minutes

  @CreateDateColumn()
  added: string;

  @OneToMany(() => Newsfeed, feed => feed.series)
  newsfeeds: Newsfeed[];

  @OneToMany(() => Season, season => season.series, {
    cascade: ['insert', 'update'],
    onUpdate: 'CASCADE'
  })
  seasons: Season[]

  @ManyToMany(() => Category, category => category.serieses, {
    cascade: true
  })
  @JoinTable({
    joinColumn: { name: "f_series_id" },
    inverseJoinColumn: { name: "f_category_id" }
  })
  categories: Category[]

  @OneToMany(() => Userseries, feed => feed.series)
  userserieses: Userseries[];

  @OneToOne(() => Image, image => image.series, {
    nullable: true,
    eager: true,
    cascade: true
  })
  @JoinColumn({
    name: "f_image_id"
  })
  image: Image;
}